package hirsizlik.mtgacollection.log4j.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;

/**
 * Simple Implementation of Logger using AbstractLogger which logs everything
 * with System.out.
 *
 * @author Markus Schagerl
 * @see Logger
 * @see AbstractLogger
 */
public class MtgaCollectionLogger extends AbstractLogger {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final Message message, final Throwable t) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final CharSequence message, final Throwable t) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final Object message, final Throwable t) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Throwable t) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object... params) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1, final Object p2) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1, final Object p2, final Object p3) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1, final Object p2, final Object p3, final Object p4) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6,
			final Object p7) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6,
			final Object p7, final Object p8) {
		return true;
	}

	@Override
	public boolean isEnabled(final Level level, final Marker marker, final String message, final Object p0,
			final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6,
			final Object p7, final Object p8, final Object p9) {
		return true;
	}

	@Override
	public void logMessage(final String fqcn, final Level level, final Marker marker, final Message message,
			final Throwable t) {
		System.out.println(message.getFormattedMessage()); // NOSONAR
		if(t != null) {
			t.printStackTrace();
		}

	}

	@Override
	public Level getLevel() {
		return Level.ALL;
	}

}
