package hirsizlik.mtgacollection.formatter;

import hirsizlik.mtgacollection.bo.Statistic;

/**
 * Interface of a Statistic Formatter. Formats a Statistic to be printed onto a
 * console.
 *
 * @author Markus Schagerl
 */
public interface StatisticFormatter {
	/**
	 * Formats the provided statistic.
	 *
	 * @param statistic the statistic to be formatted
	 * @return the statistic as pretty string
	 */
	String format(Statistic statistic);
}
