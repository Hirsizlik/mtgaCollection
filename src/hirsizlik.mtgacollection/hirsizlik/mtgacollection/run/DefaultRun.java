package hirsizlik.mtgacollection.run;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hirsizlik.mtgacollection.bo.CardInfo;
import hirsizlik.mtgacollection.bo.SetInfo;
import hirsizlik.mtgacollection.bo.SetStatistic;
import hirsizlik.mtgacollection.bo.Statistic;
import hirsizlik.mtgacollection.bo.TotalStatistic;
import hirsizlik.mtgacollection.bo.inventory.Inventory;
import hirsizlik.mtgacollection.database.MtgaCollectionDbDAO;
import hirsizlik.mtgacollection.database.SetInfoLoader;
import hirsizlik.mtgacollection.formatter.AsciiStatisticFormatter;
import hirsizlik.mtgacollection.formatter.BasicStatisticFormatter;
import hirsizlik.mtgacollection.formatter.StatisticFormatter;
import hirsizlik.mtgacollection.mtgatrackerdaemon.MtgaTrackerDaemonDAO;
import hirsizlik.mtgacollection.parser.LogfileParser;

/**
 * The default run. Reads the log file, then prints the contents of the inventory and set statistics.
 *
 * @author Markus Schagerl
 */
public class DefaultRun implements Run{

	private static final Logger logger = LogManager.getLogger();

	private final Path toLog;
	private final MtgaCollectionDbDAO mtgaCollectionDbDAO;
	private final boolean colors;
	private final MtgaTrackerDaemonDAO mtgaTrackerDaemon;

	/**
	 * Creates the run.
	 *
	 * @param p the properties, "log.path" to read the log file and
	 * "colors" to determine if ANSI colors should be used
	 * @param mtgaCollectionDbDAO access to the database
	 */
	public DefaultRun(final Properties p, final MtgaCollectionDbDAO mtgaCollectionDbDAO) {
		this.toLog = Paths.get(p.getProperty("log.path"));
		this.colors = Boolean.parseBoolean(p.getProperty("colors"));
		this.mtgaCollectionDbDAO = mtgaCollectionDbDAO;
		this.mtgaTrackerDaemon = new MtgaTrackerDaemonDAO(p.getProperty("mtga.tracker.daemon.url"));
	}

	@Override
	public void run() throws RunException {
		try {
			startRun();
		} catch(Exception e) {
			throw new RunException(e);
		}
	}

	private void startRun() throws SQLException, IOException{
		SetInfoLoader setInfoLoader = new SetInfoLoader(mtgaCollectionDbDAO.getSetMap());

		LogfileParser lfp = LogfileParser.parse(toLog);
		Inventory inv = lfp.getInventory();
		logger.info(inv::asFormattedString);

		if (!mtgaTrackerDaemon.isMtgaRunning()) {
			logger.error("MTG Arena has to be running to load card statistics");
			return;
		}
		Map<Integer, Integer> cardAmountMap = mtgaTrackerDaemon.getCards();
		Map<SetInfo, List<CardInfo>> cardsBySet = new HashMap<>();
		setInfoLoader.getAllSets().forEach(x -> cardsBySet.put(x, new ArrayList<>(124)));
		cardAmountMap.keySet().forEach(id -> {
			Optional<CardInfo> oc;
			try {
				oc = mtgaCollectionDbDAO.getCard(id, setInfoLoader);
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}

			oc.ifPresentOrElse(c -> cardsBySet.get(c.set()).add(c),
					() -> logger.warn("Unknown card: {}", id));
		});

		LocalDate standardStart = determineStandardStart(setInfoLoader);
		List<Statistic> setStatistics = setInfoLoader.getAllSets()
			.stream()
			.<Statistic>map(s -> new SetStatistic(s, cardsBySet.get(s), cardAmountMap, standardStart))
			.toList();

		StatisticFormatter formatter = colors ? new AsciiStatisticFormatter() : new BasicStatisticFormatter();
		setStatistics.stream()
			.filter(Statistic::isAnyCardOwned)
			.filter(ss -> ss.getTotal() > 0)
			.forEach(ss -> logger.info(formatter.format(ss)));

		logger.info("---");
		printStatisticsByFormat(setStatistics, formatter);
	}

	private void printStatisticsByFormat(final List<Statistic> setStatistics, final StatisticFormatter formatter) {
		List<Statistic> inStandard = new ArrayList<>(10);
		List<Statistic> inAlchemy = new ArrayList<>(20);
		List<Statistic> inPioneer = new ArrayList<>(40);
		List<Statistic> inHistoric = new ArrayList<>(110);
		for (Statistic s : setStatistics) {
			if (s.isInStandard()) {
				inStandard.add(s);
			}
			if (s.isInAlchemy()) {
				inAlchemy.add(s);
			}
			if (s.isInPioneer()) {
				inPioneer.add(s);
			}
			inHistoric.add(s);
		}

		TotalStatistic standard = new TotalStatistic("Standard", inStandard);
		TotalStatistic alchemy = new TotalStatistic("Alchemy", inAlchemy);
		TotalStatistic pioneer = new TotalStatistic("Pioneer", inPioneer);
		TotalStatistic historic = new TotalStatistic("Historic", inHistoric);
		Stream.of(standard, alchemy, pioneer, historic)
			.map(formatter::format)
			.forEach(logger::info);
	}

	private LocalDate determineStandardStart(final SetInfoLoader setInfoLoader) {
		List<SetInfo> standardCandidates = setInfoLoader.getAllSets().stream()
				.filter(s -> !s.isSupplemental())
				// standard always starts with a fall set, so either in September or October
				.filter(s -> s.release().getMonth() == Month.SEPTEMBER || s.release().getMonth() == Month.OCTOBER)
				.sorted(Comparator.comparing(SetInfo::release).reversed())
				.toList();

		// standard starts with the 2nd to last fall set
		return standardCandidates.get(1).release();
	}

	@Override
	public String getDescription() {
		return "Prints the inventory and statistics for each set.";
	}
}
