package hirsizlik.mtgacollection.formatter;

import hirsizlik.mtgacollection.bo.Rarity;

/**
 * Utility class which maps a color to each Rarity used in AsciiStringHelper.
 *
 * @author Markus Schagerl
 * @see AsciiStringHelper
 */
public class RarityToColor {

	private RarityToColor() {
	}

	/**
	 * Maps the rarity to a color. Those being:
	 * <ul>
	 * <li>Common &rarr; White</li>
	 * <li>Uncommon &rarr; Cyan</li>
	 * <li>Rare &rarr; Yellow</li>
	 * <li>Mythic &rarr; Red</li>
	 * <li>any other rarity &rarr; default color</li>
	 * </ul>
	 *
	 * @param r the rarity
	 * @return the color
	 */
	public static String getColor(final Rarity r) {
		return switch(r) {
		case COMMON -> "@WHITE";
		case UNCOMMON -> "@CYAN";
		case RARE -> "@YELLOW";
		case MYTHIC -> "@RED";
		default -> "";
		};
	}
}
