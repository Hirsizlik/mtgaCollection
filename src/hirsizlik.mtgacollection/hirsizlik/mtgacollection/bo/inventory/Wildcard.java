package hirsizlik.mtgacollection.bo.inventory;

import hirsizlik.mtgacollection.bo.Rarity;

/**
 * Class for wildcards and the current trackPosition.
 *
 * @author Markus Schagerl
 */
public record Wildcard(
		/** amount of common wildcards */
		int common,
		/** amount of uncommon wildcards */
		int uncommon,
		/** amount of rare wildcards */
		int rare,
		/** amount of mythic rare wildcards */
		int mythic,
		/** the current trackPosition*/
		int trackPosition) {

	public Wildcard {
		if (trackPosition < 0 || trackPosition > 29) {
			throw new IllegalArgumentException("TrackPosition must be a value from 0-29.");
		}
	}

	/**
	 * Returns the amount of cards of a certain rarity.
	 *
	 * @param rarity the rarity to check
	 * @return the amount of wildcards of that rarity, or 0 if the rarity has no respective wildcard
	 */
	public int getAmountOf(final Rarity rarity) {
		return switch (rarity) {
		case COMMON -> common;
		case UNCOMMON -> uncommon;
		case RARE -> rare;
		case MYTHIC -> mythic;
		default -> 0;
		};
	}

	/**
	 * Returns the amount of pack openings required to acquire a new wildcard of the given rarity.
	 *
	 * @param rarity the rarity of the wildcard
	 * @return the amount of pack openings to the next wildcard, or 0 if there are no such wildcards.
	 */
	public int getAmountToNext(final Rarity rarity) {
		return switch (rarity) {
		// uncommon -> 2 8 14 20 26
		case UNCOMMON -> 6 + (-trackPosition - 4) % 6;
		// rare -> 5 11 23 29
		case RARE -> calculateAmountToNextRare();
		// mythic -> 17
		case MYTHIC -> 30 + (-trackPosition - 13) % 30;
		default -> 0;
		};
	}

	private int calculateAmountToNextRare() {
		if (trackPosition < 11 || trackPosition >= 23) {
			return 6 + (-trackPosition - 1) % 6;
		} else { // necessary as 17 produces a mythic rare wildcard instead
			return 12 + (-trackPosition - 13) % 12;
		}
	}
}
