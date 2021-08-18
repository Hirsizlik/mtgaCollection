package hirsizlik.mtgacollection.formatter;

import hirsizlik.mtgacollection.bo.Rarity;
import hirsizlik.mtgacollection.bo.Statistic;

/**
 * Formats a statistic in a basic (but not ugly) form and fashion.
 * @author Markus Schagerl
 */
public class BasicStatisticFormatter implements StatisticFormatter{

	@Override
	public String format(final Statistic statistic){
		StringBuilder sb = new StringBuilder(200).append(
				String.format("%s - %s / %s (%.1f%%)%n",
						statistic.getName(), statistic.getOwnedTotal(),
						statistic.getTotal(), statistic.getPercentageTotal()));
		Rarity.getCommonToMythicStream()
				.filter(r -> statistic.getOwned(r) > 0)
				.map(r -> getStringForRarity(r, statistic))
				.forEach(sb::append);
		return sb.toString();
	}

	private String getStringForRarity(final Rarity r, final Statistic statistic) {
		return String.format("%s: %s / %s (%.1f%%)%n",
				r.getName(),
				statistic.getOwned(r),
				statistic.getTotal(r),
				statistic.getPercentageOwned(r));
	}
}
