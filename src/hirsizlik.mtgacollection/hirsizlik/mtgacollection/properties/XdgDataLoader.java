package hirsizlik.mtgacollection.properties;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Loads the files following the XDG specification from $XDG_CONFIG_HOME, or if not set from $HOME/.config
 * A subfolder named "MTGA-Collection" will be used.
 *
 * @author Markus Schagerl
 */
class XdgDataLoader implements DataLoader {

	private static final String XDG_CONFIG_HOME_VAR = "XDG_CONFIG_HOME";
	private static final String XDG_DATA_HOME_VAR = "XDG_DATA_HOME";
	private static final String MTGA_COLLECTION_DIR_NAME = "MTGA-Collection";
	private static final Path REL_DATABASE_PATH = Path.of("mtgacollection.db");
	private static final Path REL_PROPERTIES_PATH = Path.of("mtgacollection.properties");

	private final Path toMtgaConfigDir;
	private final Path toMtgaDataDir;

	XdgDataLoader() {
		toMtgaConfigDir = getMtgaConfigDirPath();
		toMtgaDataDir = getMtgaDataDirPath();
	}

	private static Path getMtgaConfigDirPath() {
		Path config = getenvPath(XDG_CONFIG_HOME_VAR).orElseGet(XdgDataLoader::getDefaultConfigDir);
		return config.resolve(MTGA_COLLECTION_DIR_NAME);
	}

	private static Path getMtgaDataDirPath() {
		Path data = getenvPath(XDG_DATA_HOME_VAR).orElseGet(XdgDataLoader::getDefaultDataDir);
		return data.resolve(MTGA_COLLECTION_DIR_NAME);
	}

	private static Path getDefaultConfigDir(){
		return getHome().resolve(".config");
	}

	private static Path getDefaultDataDir() {
		return getHome().resolve(".local/share");
	}

	private static Path getHome() {
		return getenvPath("HOME").orElseThrow(() -> new IllegalStateException("HOME is not set!"));
	}

	private static Optional<Path> getenvPath(final String varName) {
		String value = System.getenv(varName);
		if(value != null)
			return Optional.of(Paths.get(value));

		return Optional.empty();
	}

	@Override
	public Path getPathToProperties() {
		return toMtgaConfigDir.resolve(REL_PROPERTIES_PATH);
	}

	@Override
	public Path getPathToDatabase() {
		return toMtgaDataDir.resolve(REL_DATABASE_PATH);
	}
}
