package hirsizlik.mtgacollection.run;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Prints the version of this jar.
 * For that, the manifest is read, using the "Implementation-Version" Field.
 * Warning, does only work if built as jar, as otherwise there is no MANIFEST.MF to read.
 * @author Markus Schagerl
 *
 */
public class VersionRun implements Run{

	private static final String IMPLEMENTATION_VERSION = "Implementation-Version";
	private static final String IMPLEMENTATION_TITLE = "Implementation-Title";
	// from pom -> groupId
	private static final String MTGACOLLECTION_IMPL_TITLE = "mtgacollection";

	private static final Logger logger = LogManager.getLogger();

	/**
	 * Creates the run. No Parameter are required.
	 */
	public VersionRun() {/* nothing to do*/}

	@Override
	public void run() throws RunException {
		try {
			startRun();
		}catch(Exception e) {
			throw new RunException(e);
		}
	}

	private void startRun() throws IOException {
		Optional<Properties> properties = getMyManifest();

		properties.stream()
				.map(x -> String.format("Version: %s", x.getOrDefault(IMPLEMENTATION_VERSION, "Unknown")))
				.findFirst()
				.ifPresentOrElse(logger::info, () -> logger.error("Version could not be detected!"));
	}

	private Optional<Properties> getMyManifest() throws IOException {
		Iterable<URL> resources = () -> {
			try {
				return this.getClass().getClassLoader().getResources("META-INF/MANIFEST.MF").asIterator();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		};

		for(URL url : resources) {
			try(InputStream inputStream = url.openStream()) {
				Properties properties = new Properties();
				properties.load(inputStream);
				if(MTGACOLLECTION_IMPL_TITLE.equals(properties.getProperty(IMPLEMENTATION_TITLE))){
					return Optional.of(properties);
				}
			}
		}

		return Optional.empty();
	}

	@Override
	public String getDescription() {
		return "Prints the version of this program.";
	}

}
