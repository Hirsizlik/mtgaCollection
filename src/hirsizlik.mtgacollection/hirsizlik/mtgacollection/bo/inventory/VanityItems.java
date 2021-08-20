package hirsizlik.mtgacollection.bo.inventory;

import java.util.List;

/**
 * All VanityItems (pets, avatars and cardbacks)
 *
 * @author Markus Schagerl
 * @see VanityItem
 * @see Pet
 * @see Vanity
 */
public record VanityItems(
		/** List of pets owned */
		List<Pet> pets,
		/** List of avatars owned */
		List<VanityItem> avatars,
		/** List of card backs owned*/
		List<VanityItem> cardBacks) {

}
