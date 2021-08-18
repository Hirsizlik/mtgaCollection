package hirsizlik.mtgacollection.run;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Prints help for each supported run.
 * @author Markus Schagerl
 *
 */
public class PrintHelpRun implements Run{

	private final List<Run> runList;
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Creates the run.
	 * @param runList the List of all runs for which help will be printed.
	 */
	public PrintHelpRun(final List<Run> runList) {
		this.runList = runList;
	}

	@Override
	public void run() {
		runList.forEach(r -> logger.info("{}: {}", r.getName(), r.getDescription()));
	}

	@Override
	public String getDescription() {
		return "Prints helpful information for each run.";
	}

}
