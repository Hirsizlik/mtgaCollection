package hirsizlik.mtgacollection.bo.inventory;

import java.util.Optional;

/**
 * Currently selected vanity items. Empty means a standard avatar, the standard card back and no pet selected
 * respectively.
 * @author Markus Schagerl
 */
public record VanitySelections(Optional<VanityItem> avatar, Optional<VanityItem> cardBack, Optional<Pet> pet) {

}
