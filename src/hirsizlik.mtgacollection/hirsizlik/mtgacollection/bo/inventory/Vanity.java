package hirsizlik.mtgacollection.bo.inventory;

/**
 * All Vanity items and their selections.
 *
 * @author Markus Schagerl
 */
public record Vanity(
		/** All vanity items*/
		VanityItems vanityItems,
		/** All currently selected vanity items */
		VanitySelections vanitySelections,
		/** The chosen basic land (which currently doesn't seem to be used at all) */
		String basicLandSet) {

}
