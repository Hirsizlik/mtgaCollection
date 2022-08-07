package hirsizlik.mtgacollection.main;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

import hirsizlik.mtgacollection.arguments.ArgumentParser;
import hirsizlik.mtgacollection.arguments.ProgramArguments;
import hirsizlik.mtgacollection.database.MtgaCollectionDbDAO;
import hirsizlik.mtgacollection.properties.DataLoader;
import hirsizlik.mtgacollection.properties.DataLoaderFactory;
import hirsizlik.mtgacollection.run.AllCardsRun;
import hirsizlik.mtgacollection.run.DefaultRun;
import hirsizlik.mtgacollection.run.ImportMtgaRun;
import hirsizlik.mtgacollection.run.InitPropertiesRun;
import hirsizlik.mtgacollection.run.PrintHelpRun;
import hirsizlik.mtgacollection.run.Run;
import hirsizlik.mtgacollection.run.RunException;
import hirsizlik.mtgacollection.run.VersionRun;

/**
 * The class with the main-Method. Here all begins. First the Arguments are
 * parsed, then the Run given by the parsed Arguments is executed.
 *
 * @author Markus Schagerl
 */
public class MTGACollectionMain {

	public static void main(final String[] args) throws Exception {
		ArgumentParser ap = new ArgumentParser(args);
		ProgramArguments pa = ap.parseArguments();
		DataLoader dl = DataLoaderFactory.getDataLoader(pa);

		if (!Files.exists(dl.getPathToProperties())) {
			new InitPropertiesRun(dl).run();
		}
		Properties properties = loadProperties(dl);
		MtgaFiles mtgaFiles = new MtgaFiles(properties);
		if (!isCurrentDatabase(dl, properties, mtgaFiles)) {
			Files.deleteIfExists(dl.getPathToDatabase());
			try (MtgaCollectionDbDAO mtgaCollectionDbDAO = new MtgaCollectionDbDAO(dl.getPathToDatabase(), false)) {
				new ImportMtgaRun(mtgaCollectionDbDAO, mtgaFiles).run();
			}
		}

		try (MtgaCollectionDbDAO mtgaCollectionDbDAO = new MtgaCollectionDbDAO(dl.getPathToDatabase(), true)) {
			List<Run> runList = createRunList(properties, mtgaCollectionDbDAO, pa.additionalArguments());
			execTheChosenOne(pa.runName(), runList);
		}
	}

	private static boolean isCurrentDatabase(final DataLoader dl, final Properties p, final MtgaFiles mf) {
		if (!Files.exists(dl.getPathToDatabase())) {
			return false;
		}

		try (MtgaCollectionDbDAO mtgaCollectionDbDAO = new MtgaCollectionDbDAO(dl.getPathToDatabase(), true)) {
			return mtgaCollectionDbDAO.checkVersionAndHashes(mf);
		}
	}

	/**
	 * Executes the chosen run. The "PrintHelpRun" is run if no run with such a name was found.
	 * @param runName the Name of the run which should be executed
	 * @param runList the list of runs
	 * @throws RunException the Exception thrown if a error occurs.
	 */
	private static void execTheChosenOne(final String runName, final List<Run> runList) throws RunException {
		Run printHelpRun = new PrintHelpRun(runList);
		runList.stream().filter(r -> runName.equals(r.getName())).reduce(printHelpRun, (r0, r1) -> {
			if(r0 != printHelpRun)
				throw new IllegalStateException("Multiple Runs with the same name were found!");
			return r1;
		}).run();
	}

	/**
	 * Creates the list of runs.
	 * @param p the properties
	 * @param mtgaCollectionDbDAO the DAO to access the database
	 * @param additionalArguments additional arguments which may be used in the runs.
	 * @return the list of runs
	 */
	private static List<Run> createRunList(final Properties p, final MtgaCollectionDbDAO mtgaCollectionDbDAO,
			final List<String> additionalArguments) {
		return List.of(
				new DefaultRun(p, mtgaCollectionDbDAO),
				new AllCardsRun(p, mtgaCollectionDbDAO, additionalArguments),
				new VersionRun());
	}

	private static Properties loadProperties(final DataLoader dl) throws IOException {
		Properties p = new Properties();
		try(InputStream is = Files.newInputStream(dl.getPathToProperties())){
			p.load(is);
		}
		return p;
	}
}
