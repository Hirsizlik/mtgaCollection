package hirsizlik.mtgacollection.run;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hirsizlik.mtgacollection.bo.CardInfo;
import hirsizlik.mtgacollection.database.SetInfoLoader;
import hirsizlik.mtgacollection.database.SqLiteDAO;
import hirsizlik.mtgacollection.jackson.mtga.card.MtgaCard;
import hirsizlik.mtgacollection.jackson.mtga.localisation.Key;
import hirsizlik.mtgacollection.jackson.mtga.localisation.Localisation;
import hirsizlik.mtgacollection.mapper.MapMtgaCardToCardInfo;
import hirsizlik.mtgacollection.mapper.MappingResult;
import hirsizlik.mtgacollection.parser.MtgaCardLocParser;
import hirsizlik.mtgacollection.parser.MtgaCardLocParser.MtgaCardLoc;
import hirsizlik.mtgacollection.scryfall.ScryfallDAO;
import hirsizlik.mtgacollection.scryfall.ScryfallSetInfo;
import hirsizlik.mtgacollection.scryfall.ScryfallSetQuirk;

/**
 * Run to import cards from MTGA (MTGA_Data/Downloads/Data)
 *
 * @author Markus Schagerl
 */
public class ImportMtgaRun implements Run {

	private static final String DATA_LOC = "Data_loc";
	private static final String DATA_CARDS = "Data_cards";
	private final Path toMtgaData;
	private final SqLiteDAO sqLiteDAO;
	private final ScryfallDAO scryfallDAO = new ScryfallDAO();

	private static final Logger logger = LogManager.getLogger();

	/**
	 * A pair with the name prefix and the path to the corresponding file.
	 */
	private static record NamePathPair(String name, Path path) {}

	/**
	 * Creates the run
	 *
	 * @param p the properties, "mtga.path" to get the path to the game files
	 * @param sqLiteDAO access to the database
	 */
	public ImportMtgaRun(final Properties p, final SqLiteDAO sqLiteDAO) {
		this.toMtgaData = Paths.get(p.getProperty("mtga.path"), "MTGA_Data/Downloads/Data/");
		this.sqLiteDAO = sqLiteDAO;
	}

	@Override
	public void run() throws RunException {
		try {
			startRun();
		} catch(Exception e) {
			Thread.currentThread().interrupt();
			throw new RunException(e);
		}
	}

	private void startRun() throws IOException, InterruptedException, SQLException {
		List<String> fileNames = List.of(DATA_CARDS, DATA_LOC);
		Map<String, Path> fileMap = getMtgaFilePaths(fileNames);
		if(fileMap.size() != fileNames.size()) {
			throw new IllegalStateException(String.format("Not all necessary files were found (found: %s)", fileMap));
		}

		MtgaCardLoc cardLoc = MtgaCardLocParser.parse(fileMap.get(DATA_CARDS), fileMap.get(DATA_LOC));

		SetInfoLoader sil = new SetInfoLoader(sqLiteDAO.getSetMap());

		if(updateSets(cardLoc.cardList(), sil)) {
			// reload if a set was added
			sil = new SetInfoLoader(sqLiteDAO.getSetMap());
		}

		MapMtgaCardToCardInfo cardMapper = new MapMtgaCardToCardInfo(
				mapLocalisation(cardLoc.englishLocalization()), sil);

		List<CardInfo> cardInfoList = cardLoc.cardList().stream().map(cardMapper)
				.filter(mr -> mr.returnTrueIfOkOrElse(this::doIfUnmappable))
				.map(MappingResult::get)
				.toList();

		addCards(sil, cardInfoList);
	}

	private void addCards(final SetInfoLoader sil, final List<CardInfo> cardInfoList) throws SQLException {
		int count = 0;
		final int batchSize = 500;
		for(CardInfo c : cardInfoList) {
			if(addCardIfNew(c, sil)) {
				count++;
			}
			if(count == batchSize)  {
				count = 0;
				sqLiteDAO.executeBatchedCards();
			}
		}
		if(count > 0) {
			sqLiteDAO.executeBatchedCards();
		}
	}

	private boolean addCardIfNew(final CardInfo c, final SetInfoLoader sil) {
		try {
			boolean cardAdded = sqLiteDAO.addCardIfNewBatched(c, sil);
			if (cardAdded) {
				logger.info("Added: {}", c);
			}
			return cardAdded;
		} catch (SQLException e) {
			throw new IllegalStateException("SQL-Error while adding " + c, e);
		}
	}

	/**
	 * Searches for unknown sets and adds them to the database.
	 *
	 * @return true if Sets were added, otherwise false
	 */
	private boolean updateSets(final List<MtgaCard> allCards, final SetInfoLoader setInfoLoader)
			throws IOException, InterruptedException, SQLException {
		Set<String> unknownSets = allCards.stream()
				.map(MtgaCard::getSet)
				.filter(s -> setInfoLoader.getByCode(s) == setInfoLoader.getUnknownSet())
				.collect(Collectors.toSet());
		if(unknownSets.isEmpty())
			return false;

		return loadAndAddUnknownSets(unknownSets);
	}

	/**
	 * Loads Set information from Scryfall and adds them to the database if found.
	 *
	 * @param unknownSets Set codes of all unknown sets.
	 */
	private boolean loadAndAddUnknownSets(final Set<String> unknownSets)
			throws IOException, InterruptedException, SQLException {
		boolean oneAdded = false;
		for(String unknownSet : unknownSets) {
			Optional<ScryfallSetInfo> setInfo = Stream.of(
						scryfallDAO.getSet(ScryfallSetQuirk.translateToScryfall(unknownSet)),
						ScryfallSetQuirk.createFakeScryfallSet(unknownSet))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.findFirst();

			if(setInfo.isPresent()) {
				sqLiteDAO.addSetFromScryfallBatch(setInfo.get());
				logger.info("{} was added", () -> setInfo.get().code());
				oneAdded = true;
			} else {
				logger.warn("Set {} could not be found in Scryfall!", unknownSet);
			}
		}
		if(oneAdded) {
			sqLiteDAO.executeBatchedSets();
		}

		return oneAdded;
	}

	private void doIfUnmappable(final MappingResult<MtgaCard, CardInfo> mr) {
		if(mr.getException().isImportant()) {
			logger.error("Error while mapping the card: {}; Error: {}", mr.getBase(),
					mr.getException());
		}
	}

	private static Map<Integer, String> mapLocalisation(final Localisation loc){
		return loc.getKeys().stream().collect(Collectors.toMap(Key::getId,
				k -> Objects.requireNonNullElse(k.getRaw(), k.getText())));
	}

	private Map<String, Path> getMtgaFilePaths(final List<String> nameStart) throws IOException{
		try(Stream<Path> fileStream = Files.walk(toMtgaData)){
			return fileStream.map(p -> checkPath(p, nameStart)).filter(Optional::isPresent).collect(
					Collectors.toMap(np -> np.get().name, np -> np.get().path));
		}
	}

	private Optional<NamePathPair> checkPath(final Path p, final List<String> nameStart) {
		String fileName = p.getFileName().toString();
		return nameStart.stream().filter(n -> fileName.startsWith(n) && fileName.endsWith(".mtga"))
				.map(n -> new NamePathPair(n, p)).findFirst();
	}

	@Override
	public String getDescription() {
		return "Imports Cards from MTGA. New Sets are loaded with Scryfall.";
	}

}
