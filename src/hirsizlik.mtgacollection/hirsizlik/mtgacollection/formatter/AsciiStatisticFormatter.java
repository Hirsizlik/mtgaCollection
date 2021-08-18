package hirsizlik.mtgacollection.formatter;

import hirsizlik.mtgacollection.bo.Rarity;
import hirsizlik.mtgacollection.bo.Statistic;

/**
 * Formats a statistic using the power of ASCII-Escape-Codes.
 * Prints the set name in bold and historic-only sets in italic.
 * Each rarity has it's own color.
 * @author Markus Schagerl
 * @see AsciiStringHelper
 * @see RarityToColor
 */
public class AsciiStatisticFormatter implements StatisticFormatter{

	@Override
	public String format(final Statistic statistic) {
		StringBuilder sb = new StringBuilder(64);
		sb.append("@BOLD");
		if(!statistic.isInStandard()) {
			sb.append("@ITALIC");
		}
		sb.append(String.format("%s - %d / %d (%.1f%%)@DEFAULT%n",
				statistic.getName(), statistic.getOwnedTotal(),
				statistic.getTotal(), statistic.getPercentageTotal()));

		Rarity.getCommonToMythicStream()
			.filter(r -> statistic.getOwned(r) > 0)
			.map(r -> getStringForRarity(r, statistic))
			.forEach(sb::append);
		return AsciiStringHelper.getAsciiString(sb.toString());
	}

	private String getStringForRarity(final Rarity r, final Statistic s) {
		return String.format("%s%s: %d / %d (%.1f%%)@DEFAULT%n", RarityToColor.getColor(r), r.getName(), s.getOwned(r),
				s.getTotal(r), s.getPercentageOwned(r));
	}
}
