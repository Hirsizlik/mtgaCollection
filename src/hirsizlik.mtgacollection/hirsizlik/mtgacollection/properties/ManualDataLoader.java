package hirsizlik.mtgacollection.properties;

import java.nio.file.Path;
import java.util.Objects;

import hirsizlik.mtgacollection.arguments.ProgramArguments;

/**
 * Loads the data from via program arguments.
 *
 * @author Markus Schagerl
 */
class ManualDataLoader implements DataLoader {

	private final Path toProperties;
	private final Path toDatabase;

	ManualDataLoader(final ProgramArguments arguments) {
		Objects.requireNonNull(arguments.pathToProperties());
		Objects.requireNonNull(arguments.pathToDatabase());

		this.toProperties = Path.of(arguments.pathToProperties());
		this.toDatabase = Path.of(arguments.pathToDatabase());
	}

	@Override
	public Path getPathToProperties() {
		return toProperties;
	}

	@Override
	public Path getPathToDatabase() {
		return toDatabase;
	}
}
