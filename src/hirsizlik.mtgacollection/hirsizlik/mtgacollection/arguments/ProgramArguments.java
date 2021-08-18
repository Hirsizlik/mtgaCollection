package hirsizlik.mtgacollection.arguments;

import java.util.List;

/**
 * Holds the given program arguments.
 */
public record ProgramArguments(String pathToProperties, String pathToDatabase, String runName,
		List<String> additionalArguments) {

}
