package hirsizlik.mtgacollection.arguments;

import java.util.List;
import java.util.Objects;

/**
 * Holds the given program arguments.
 *
 * @author Markus Schagerl
 */
public record ProgramArguments(
		/** Given path to the properties file, may be null */
		String pathToProperties,
		/** Given path to the database file, may be null */
		String pathToDatabase,
		/** Given name of the run to be executed, must not be null */
		String runName,
		/** Any additional arguments given, may be empty but not null */
		List<String> additionalArguments) {

	public ProgramArguments {
		Objects.requireNonNull(runName);
		Objects.requireNonNull(additionalArguments);
	}
}
