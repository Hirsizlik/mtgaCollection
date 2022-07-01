package hirsizlik.mtgacollection.run;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hirsizlik.mtgacollection.database.MtgaCollectionDbDAO;
import hirsizlik.mtgacollection.properties.DataLoader;

/**
 * Run to initiate the database and the properties.
 *
 * @author Markus Schagerl
 */
public class InitRun implements Run {

	private final DataLoader dataloader;
	private static final Logger LOG = LogManager.getLogger();
	private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

	private enum PathQuestionType {
		FILE, DIRECTORY;
	}

	public InitRun(final DataLoader dataLoader) {
		this.dataloader = dataLoader;
	}

	@Override
	public void run() throws RunException {
		try {
			runInternal();
		} catch(Exception e) {
			throw new RunException(e);
		}
	}

	private void runInternal() throws IOException, SQLException {
		// check properties
		if(!Files.exists(dataloader.getPathToProperties())) {
			LOG.warn("Properties file is missing and will be created at {}", dataloader.getPathToProperties());
			createPropertiesFile();
		}
		// check database
		if(!Files.exists(dataloader.getPathToDatabase())) {
			LOG.warn("Database is missing and will be created at {}", dataloader.getPathToDatabase());
			createDatabase();
		}
	}

	private void createPropertiesFile() throws IOException {
		Path pathToLog = askPathQuestion("""
				Please enter the path to the MTGA Player.log file. E.g. \
				'/home/user/.wine/drive_c/users/user/AppData/LocalLow/Wizards Of The Coast/MTGA/Player.log'
				""", PathQuestionType.FILE);
		Path pathToGame = askPathQuestion("""
				Please enter the path to MTGA. E.g. \
				/home/user/.wine/drive_c/Program Files/Wizards of the Coast/MTGA/
				""", PathQuestionType.DIRECTORY);
		boolean color = askYesNoQuestion("Do you want colors?");

		Properties props = new Properties();
		props.setProperty("log.path", pathToLog.toAbsolutePath().toString());
		props.setProperty("mtga.path", pathToGame.toAbsolutePath().toString());
		props.setProperty("colors", Boolean.toString(color));
		props.setProperty("mtga.tracker.daemon.url", "http://localhost:6842"); // daemon-default
		try (OutputStream os = Files.newOutputStream(dataloader.getPathToProperties()))  {
			props.store(os, "");
		}
	}

	private Path askPathQuestion(final String question, final PathQuestionType type) {
		do {
			LOG.warn("{} {}", question, "(/path/to/file):");
			String answer = scanner.nextLine();
			try {
				Path p = Path.of(answer);
				boolean valid = switch(type) {
				case FILE -> Files.isRegularFile(p);
				case DIRECTORY -> Files.isDirectory(p);
				};
				if(valid) {
					return p;
				} else {
					LOG.error("The file should be a {}", () -> type.toString().toLowerCase(Locale.ENGLISH));
				}
			} catch(InvalidPathException e) {
				LOG.error(e.getMessage());
			}
		} while(true);
	}

	private boolean askYesNoQuestion(final String question) {
		String answer;
		do {
			LOG.warn("{} {}", question, "(Y/N):");
			answer = scanner.nextLine();

			if(!"Y".equalsIgnoreCase(answer) && !"N".equalsIgnoreCase(answer)) {
				LOG.warn("Please answer with Y or N");
			} else {
				break;
			}
		} while(true);
		return "Y".equalsIgnoreCase(answer);
	}

	private void createDatabase() throws SQLException {
		try(MtgaCollectionDbDAO mtgaCollectionDbDAO = new MtgaCollectionDbDAO(dataloader.getPathToDatabase())){
			mtgaCollectionDbDAO.createTables();
		}
	}

	@Override
	public String getDescription() {
		return "Starts the initiation. The properties and the database will be created";
	}

}
