package hirsizlik.mtgacollection.properties;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Interface to load necessary data.
 */
public interface DataLoader {
	/**
	 * @return path to the properties file
	 */
	Path getPathToProperties();

	/**
	 * @return path to the sqLite database file.
	 */
	Path getPathToDatabase();

	/**
	 * @return true if both files actually exist, otherwise false
	 */
	default boolean doFilesExist() {
		return Files.exists(getPathToProperties()) && Files.exists(getPathToDatabase());
	}

	/**
	 * @return true if either the database or the properties file is missing
	 */
	default boolean isSomethingMissing() {
		return !doFilesExist();
	}
}
