package hirsizlik.mtgacollection.run;

/**
 * Exception for errors during a run.
 * @author Markus Schagerl
 *
 */
public class RunException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a RunException with a cause
	 * @param cause the cause
	 */
	public RunException(final Exception cause) {
		super(cause);
	}
}
