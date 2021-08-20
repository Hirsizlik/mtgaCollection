package hirsizlik.mtgacollection.arguments;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Reads the given program arguments and parses them.
 *
 * @author Markus Schagerl
 * @see ProgramArguments
 */
public class ArgumentParser {

	private final String[] args;

	private static final String USAGE = "Usage: [-p Properties-Path Database-Path] "+
			"[-r Run-Name] [-- Additional Arguments]";
	private static record Paths(String properties, String database) {}

	/**
	 * Initializes the parser with the program arguments.
	 * To parse, use the {@link #parseArguments()} method.
	 *
	 * @param args the program arguments (from main)
	 */
	public ArgumentParser(final String[] args) {
		this.args = args;
	}

	/**
	 * Parses the arguments from the array given in the constructor.
	 *
	 * @return the parsed arguments
	 */
	public ProgramArguments parseArguments() {
		if(args.length == 0) {
			// shortcut for the no-argument case
			return new ProgramArguments(null, null, "Default", List.of());
		}

		Optional<Paths> p = getPArgument();
		Optional<String> r = getRArgument();
		List<String> a = getAdditionalArguments();

		String properties;
		String database;

		if(p.isPresent()) {
			properties = p.get().properties;
			database = p.get().database;
		} else {
			properties = null;
			database = null;
		}

		return new ProgramArguments(properties, database, r.orElse("Default"), a);
	}

	private Optional<String> getRArgument() {
		int i = indexOf("-r");
		if(i == -1) {
			return Optional.empty();
		}

		assertArgumentsAfterIndex(i, 1, "Runname expected after -r!");
		return Optional.of(args[i+1]);
	}

	private Optional<Paths> getPArgument() {
		int i = indexOf("-p");
		if(i == -1) {
			return Optional.empty();
		}
		assertArgumentsAfterIndex(i, 2, "2 paths expected after -p!");

		return Optional.of(new Paths(args[i+1], args[i+2]));
	}

	private List<String> getAdditionalArguments() {
		int i = indexOf("--");
		if (i == -1) {
			return List.of();
		}

		return Arrays.asList(Arrays.copyOfRange(args, i, args.length));
	}

	private int indexOf(final String argument) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(argument)) {
				return i;
			}
		}

		return -1;
	}

	private void assertArgumentsAfterIndex(final int index, final int amountExpected, final String errorMessage) {
		if(args.length < index + amountExpected) {
			throw new IllegalArgumentException(errorMessage + USAGE);
		}

		for(int i = index + 1; i < index + amountExpected; i++) {
			if(args[i].startsWith("-")) {
				throw new IllegalArgumentException(errorMessage + USAGE);
			}
		}
	}
}
