package hirsizlik.mtgacollection.log4j.logger;

import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.LoggerContext;

/**
 * A simple LoggerContext for log4j2, always returning a new Logger at getLogger.
 * @author Markus Schagerl
 *
 */
public class MtgaCollectionLoggerContext implements LoggerContext{

	@Override
	public Object getExternalContext() {
		return "MtgaCollectionExternalContext";
	}

	@Override
	public ExtendedLogger getLogger(final String name) {
		return new MtgaCollectionLogger();
	}

	@Override
	public ExtendedLogger getLogger(final String name, final MessageFactory messageFactory) {
		return new MtgaCollectionLogger();
	}

	@Override
	public boolean hasLogger(final String name) {
		return false;
	}

	@Override
	public boolean hasLogger(final String name, final MessageFactory messageFactory) {
		return false;
	}

	@Override
	public boolean hasLogger(final String name, final Class<? extends MessageFactory> messageFactoryClass) {
		return false;
	}

}
