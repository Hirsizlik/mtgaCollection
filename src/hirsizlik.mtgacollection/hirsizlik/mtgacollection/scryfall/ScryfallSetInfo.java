package hirsizlik.mtgacollection.scryfall;

import java.time.LocalDate;

/**
 * Set information from Scryfall.
 *
 * @author Markus Schagerl
 */
public record ScryfallSetInfo(
		/** The name of the set*/
		String name,
		/** The set code */
		String code,
		/** The type of set */
		ScryfallSetType type,
		/** The release date */
		LocalDate releasedAt) {

}
