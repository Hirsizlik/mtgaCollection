package hirsizlik.mtgacollection.run;

/**
 * Interface for each run.
 * @author Markus Schagerl
 *
 */
public interface Run {
	/**
	 * Executes the run.
	 * @throws RunException thrown for any unrecoverable error during the run
	 */
	void run() throws RunException;
	/**
	 * @return the name of the run.
	 * <p>
	 * Default: Classname excluding the last three letters, which should be "Run".
	 * </p>
	 * <p>
	 * example: ExampleRun &rarr; "Example"
	 * </p>
	 */
	default String getName() {
		String name = this.getClass().getSimpleName();
		return this.getClass().getSimpleName().substring(0, name.length() - 3);
	}
	/**
	 * @return a description of the run.
	 */
	String getDescription();
}
