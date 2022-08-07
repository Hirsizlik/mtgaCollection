package hirsizlik.mtgacollection.run;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hirsizlik.mtgacollection.bo.CardInfo;
import hirsizlik.mtgacollection.database.MtgaCollectionDbDAO;
import hirsizlik.mtgacollection.database.RawCardDatabaseDAO;
import hirsizlik.mtgacollection.database.SetInfoLoader;
import hirsizlik.mtgacollection.jackson.mtga.card.MtgaCard;
import hirsizlik.mtgacollection.main.MtgaFiles;
import hirsizlik.mtgacollection.mapper.MapMtgaCardToCardInfo;
import hirsizlik.mtgacollection.parser.MtgaCardParser;
import hirsizlik.mtgacollection.scryfall.ScryfallDAO;
import hirsizlik.mtgacollection.scryfall.ScryfallSetInfo;
import hirsizlik.mtgacollection.scryfall.ScryfallSetQuirk;

/**
 * Run to import cards from MTGA (MTGA_Data/Downloads/Data)
 *
 * @author Markus Schagerl
 */
public class ImportMtgaRun implements Run {

	private final MtgaCollectionDbDAO mtgaCollectionDbDAO;
	private final MtgaFiles mtgaFiles;
	private final ScryfallDAO scryfallDAO = new ScryfallDAO();

	private static final Logger logger = LogManager.getLogger();

	/**
	 * Creates the run
	 *
	 * @param mtgaCollectionDbDAO access to the database
	 */
	public ImportMtgaRun(final MtgaCollectionDbDAO mtgaCollectionDbDAO, final MtgaFiles mtgaFiles) {
		this.mtgaCollectionDbDAO = mtgaCollectionDbDAO;
		this.mtgaFiles = mtgaFiles;
	}

	@Override
	public void run() throws RunException {
		try {
			startRun();
		} catch (Exception e) {
			Thread.currentThread().interrupt();
			throw new RunException(e);
		}
	}

	private void startRun() throws IOException, InterruptedException {
		mtgaCollectionDbDAO.createTables();// errors if it already exists

		try (RawCardDatabaseDAO rawCardDatabase = new RawCardDatabaseDAO(mtgaFiles.getCardDatabasePath())) {
			List<MtgaCard> cardData = MtgaCardParser.parse(mtgaFiles.getCardsPath());
			addSets(cardData);

			SetInfoLoader sil = new SetInfoLoader(mtgaCollectionDbDAO.getSetMap(true, true));
			MapMtgaCardToCardInfo cardMapper = new MapMtgaCardToCardInfo(rawCardDatabase, sil);

			List<CardInfo> cardInfoList = cardData.stream()
					.map(cardMapper)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.toList();

			addCards(cardInfoList);
		}
		mtgaCollectionDbDAO.setHashes(mtgaFiles);
	}

	private void addCards(final List<CardInfo> cardInfoList) {
		int count = 0;
		final int batchSize = 500;
		for(CardInfo c : cardInfoList) {
			mtgaCollectionDbDAO.addCardBatched(c);
			logger.info("Added: {}", c);
			count++;
			if (count == batchSize) {
				count = 0;
				mtgaCollectionDbDAO.executeBatchedCards();
			}
		}
		if (count > 0) {
			mtgaCollectionDbDAO.executeBatchedCards();
		}
	}

	/**
	 * Collects all sets from the card list and adds them to the database.
	 *
	 * @return true if Sets were added, otherwise false
	 * @throws IllegalStateException if a set could not be added
	 */
	private void addSets(final List<MtgaCard> allCards) throws IOException, InterruptedException {
		Set<String> allSets = allCards.stream()
				.flatMap(mc -> Stream.of(mc.getSet(), mc.getDigitalReleaseSet()))
				.filter(Objects::nonNull)// DigitalReleaseSet can be null, filter those out
				.collect(Collectors.toSet());

		for (String set : allSets) {
			ScryfallSetInfo setInfo = Stream.of(
						scryfallDAO.getSet(ScryfallSetQuirk.translateToScryfall(set)),
						ScryfallSetQuirk.createFakeScryfallSet(set))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("set %s could not be added".formatted(set)));

			mtgaCollectionDbDAO.addSetFromScryfallBatch(setInfo);
			logger.info("{} was added", setInfo::code);
		}
		mtgaCollectionDbDAO.executeBatchedSets();
	}

	@Override
	public String getDescription() {
		return "Imports alls cards and sets from MTGA. Additional data for sets is loaded from Scryfall.";
	}

}
