package hirsizlik.mtgacollection.log4j.logger;

import org.apache.logging.log4j.spi.Provider;

/**
 * A Log4j2 Provider providing the MtgaCollectionLoggerContextFactory with a low
 * priority.
 *
 * @author Markus Schagerl
 */
public class MtgaCollectionLoggerProvider extends Provider {

	public MtgaCollectionLoggerProvider() {
		super(-100, "2.6.0", MtgaCollectionLoggerContextFactory.class);
	}
}
