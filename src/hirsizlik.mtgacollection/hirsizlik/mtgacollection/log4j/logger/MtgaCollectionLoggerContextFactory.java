package hirsizlik.mtgacollection.log4j.logger;

import java.net.URI;

import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;

/**
 * A simple LoggerContextFactory which always returns the same MtgaCollectionLoggerContext created at instantiation.
 * @author Markus Schagerl
 *
 */
public class MtgaCollectionLoggerContextFactory implements LoggerContextFactory {

	private MtgaCollectionLoggerContext contextImpl = new MtgaCollectionLoggerContext();

	@Override
	public LoggerContext getContext(final String fqcn, final ClassLoader loader, final Object externalContext,
			final boolean currentContext) {
		return contextImpl;
	}

	@Override
	public LoggerContext getContext(final String fqcn, final ClassLoader loader, final Object externalContext,
			final boolean currentContext, final URI configLocation, final String name) {
		return contextImpl;
	}

	@Override
	public void removeContext(final LoggerContext context) {
		if(context == contextImpl) {
			contextImpl = null;
		}
	}

}
