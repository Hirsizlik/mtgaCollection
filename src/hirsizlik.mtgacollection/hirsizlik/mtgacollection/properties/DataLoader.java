package hirsizlik.mtgacollection.properties;

import java.nio.file.Path;

/**
 * Interface to load necessary data.
 *
 * @author Markus Schagerl
 */
public interface DataLoader {
	/**
	 * @return path to the properties file
	 */
	Path getPathToProperties();

	/**
	 * @return path to the local sqLite database file.
	 */
	Path getPathToDatabase();
}
