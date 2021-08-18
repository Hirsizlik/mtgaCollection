package hirsizlik.mtgacollection.bo;

import hirsizlik.mtgacollection.database.SetInfoLoader;

/**
 * Information about a card from a specific set.
 * @author Markus Schagerl
 *
 */
public record CardInfo(int id, String name, Rarity rarity, SetInfo set, boolean inBooster) {

	/**
	 * Creates a "unknown" card. Used as a placeholder if the real card cannot be found.
	 * @param id the id of the unknown card
	 * @param setInfoLoader the setInfoLoader to load the unknown set
	 * @return the new unknown card with the given id
	 */
	public static CardInfo newUnknown(final int id, final SetInfoLoader setInfoLoader) {
		return new CardInfo(id, "Unknown", Rarity.UNKNOWN, setInfoLoader.getUnknownSet(), true);
	}
}
