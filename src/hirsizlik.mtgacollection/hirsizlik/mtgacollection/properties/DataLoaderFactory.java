package hirsizlik.mtgacollection.properties;

import hirsizlik.mtgacollection.arguments.ProgramArguments;

/**
 * Factory class to create a data loader.
 *
 * @author Markus Schagerl
 * @see DataLoader
 */
public class DataLoaderFactory {

	private DataLoaderFactory () {}

	/**
	 * Creates a DataLoader based on program arguments and the operating system.
	 *
	 * @param arguments die program arguments
	 * @return the created data loader
	 */
	public static DataLoader getDataLoader(final ProgramArguments arguments) {
		if(arguments.pathToProperties() != null && arguments.pathToDatabase() != null) {
			return new ManualDataLoader(arguments);
		}

		return getDataLoaderByOsName(System.getProperty("os.name"));
	}

	private static DataLoader getDataLoaderByOsName(final String osName) {
		return switch(osName)  {
			case "Linux" -> new XdgDataLoader();
			default -> throw new IllegalArgumentException(osName + " is not supported, use -p instead.");
		};
	}
}
