package hirsizlik.mtgacollection.run;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hirsizlik.mtgacollection.bo.CardInfo;
import hirsizlik.mtgacollection.bo.Rarity;
import hirsizlik.mtgacollection.database.MtgaCollectionDbDAO;
import hirsizlik.mtgacollection.database.SetInfoLoader;
import hirsizlik.mtgacollection.formatter.AsciiStringHelper;
import hirsizlik.mtgacollection.formatter.RarityToColor;
import hirsizlik.mtgacollection.parser.LogfileParser;

/**
 * Run which prints all cards currently in possession.
 *
 * @author Markus Schagerl
 */
public class AllCardsRun implements Run{

	private final Path toLog;
	private final MtgaCollectionDbDAO mtgaCollectionDbDAO;

	private static final Logger logger = LogManager.getLogger();

	/**
	 * A value for each possible filter containing its implementation as BiPredicate.
	 */
	private enum Filter{
		RARITY((c, n) -> n.equals(c.cardInfo.rarity().getName())),
		SET((c, n) -> n.equals(c.cardInfo.set().code())),
		NAME((c, n) -> c.cardInfo.name().toLowerCase(Locale.ENGLISH).contains(n));

		Filter(final BiPredicate<CardInfoAndAmount, String> p) {
			this.biPredicate = p;
		}

		BiPredicate<CardInfoAndAmount, String> biPredicate;

	}
	private final EnumMap<Filter, List<String>> filterMap;
	private final List<String> additionalArguments;
	private final boolean color;

	/**
	 * Creates the run.
	 *
	 * @param p the properties, using "log.path" to read the log file and "colors" to decide if ANSI colors
	 * should be used.
	 * @param mtgaCollectionDbDAO access to the local SqLite-Database
	 * @param additionalArguments additional Arguments, which can contain filter criteria.
	 */
	public AllCardsRun(final Properties p, final MtgaCollectionDbDAO mtgaCollectionDbDAO,
			final List<String> additionalArguments) {
		toLog = Paths.get(p.getProperty("log.path"));
		color = Boolean.parseBoolean(p.getProperty("colors"));
		filterMap = new EnumMap<>(Filter.class);
		Stream.of(Filter.values()).forEach(f -> filterMap.put(f, new ArrayList<>()));
		this.mtgaCollectionDbDAO = mtgaCollectionDbDAO;
		this.additionalArguments = additionalArguments;
	}

	private boolean additionalArgumentsContainsIgnoreCase(final String contained) {
		return additionalArguments.stream().anyMatch(s -> s.equalsIgnoreCase(contained));
	}

	@Override
	public void run() throws RunException {
		try {
			startRun();
		}catch (Exception e) {
			throw new RunException(e);
		}
	}

	private void startRun() throws SQLException, IOException {
		SetInfoLoader setInfoLoader = new SetInfoLoader(mtgaCollectionDbDAO.getSetMap());

		initFilterMap(setInfoLoader);

		if(filterMap.values().stream().anyMatch(v -> !v.isEmpty())) {
			logger.info("Filter: ");
			filterMap.forEach((k, v) -> logger.printf(Level.INFO, "%-6s: %s", k, v));
			logger.info("---");
		}

		Predicate<CardInfoAndAmount> filter = createCombinedFilter();

		LogfileParser lfp = LogfileParser.parse(toLog);

		Map<Integer, Integer> cardAmountMap = lfp.getCardIdAmountMap();

		Function<CardInfoAndAmount, String> ciaaFormatter = createFormatter();

		cardAmountMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
			.map(e -> mapCard(setInfoLoader, e))
			.filter(filter)
			.map(ciaaFormatter)
			.forEach(logger::info);
	}

	private void initFilterMap(final SetInfoLoader setInfoLoader) {
		Rarity.getCommonToMythicStream()
			.filter(r -> additionalArgumentsContainsIgnoreCase(r.getName()))
			.forEach(r -> filterMap.get(Filter.RARITY).add(r.getName()));

		setInfoLoader.getAllSets().stream()
			.filter(s -> additionalArgumentsContainsIgnoreCase(s.code()))
			.forEach(s -> filterMap.get(Filter.SET).add(s.code()));

		additionalArguments.stream().filter(arg -> arg.startsWith("n:")).forEach(arg ->
			filterMap.get(Filter.NAME).add(arg.substring(2).toLowerCase(Locale.ENGLISH)));
	}

	private Function<CardInfoAndAmount, String> createFormatter() {
		if (color) {
			return c -> AsciiStringHelper.getAsciiString(String.format("%s%s@DEFAULT",
					RarityToColor.getColor(c.cardInfo.rarity()), c.toString()));
		} else {
			return CardInfoAndAmount::toString;
		}
	}

	private Predicate<CardInfoAndAmount> createCombinedFilter(){
		return filterMap.entrySet().stream().map(e -> {
			if(!e.getValue().isEmpty()) {
				return (Predicate<CardInfoAndAmount>) c -> e.getValue().stream()
						.anyMatch(n -> e.getKey().biPredicate.test(c, n));
			}else {
				return (Predicate<CardInfoAndAmount>) c -> true;
			}
		}).reduce(c -> true, Predicate::and);
	}

	static record CardInfoAndAmount(CardInfo cardInfo, int amount){
		@Override
		public String toString() {
			return String.format("%5d - %-30s %-25s - %d",
					cardInfo.id(), cardInfo.name(), cardInfo.set().name(), amount);
		}
	}

	private CardInfoAndAmount mapCard(final SetInfoLoader sil, final Map.Entry<Integer, Integer> entry) {
		try {
			Optional<CardInfo> ci = mtgaCollectionDbDAO.getCard(entry.getKey(), sil);
			return new CardInfoAndAmount(ci.orElse(CardInfo.newUnknown(entry.getKey(), sil)), entry.getValue());
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String getDescription() {
		return "Prints all currently owned cards with their amount. "
				+ "These can be filtered over set, rarity and name using additional arguments. "
				+ "Names require the prefix \"n:\". Different kind of filter are linked with AND, "
				+ "same kind are linked with OR. "
				+ "(for example: 'common rare' prints all commons and all rares. "
				+ "'common JMP' prints alle commons from Jumpstart. "
				+ "'common JMP n:\"Sylvan \"' prints all commons from Jumpstart having \"Sylvan \" in their name.) "
				+ "Filter are case insensitive.";
	}

}
