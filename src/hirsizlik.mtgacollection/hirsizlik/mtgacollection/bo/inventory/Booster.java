package hirsizlik.mtgacollection.bo.inventory;

/**
 * Describes the amount of booster of a set currently owned.
 * <p>
 * Note about collationId:
 * There a 6 groups of collationIds:
 * <ol>
 * <li>&lt; 100000: "KLD" and "AER" from the Arena beta
 * <li>100000 - 199999: Premier packs from the shop
 * <li>200000 - 299999: Draft packs (used during draft)
 * <li>300000 - 399999: Cube packs
 * <li>400000 - 499999: Alchemy packs
 * <li>500000 - 599999: Mythic packs
 * </ol>
 * @author Markus Schagerl
 */
public record Booster(
		/** the amount of booster packs currently owned*/
		int count,
		/** the collationId, used to determine if a mythic pack or not */
		Integer collationId,
		/** the set code (MID, Y22NEO, etc.)*/
		String set) {

	/**
	 * @return a short string describing this booster.
	 * <p>
	 * Format is {@code <set code>: <amount of booster>}"
	 */
	public String getShortString() {
		if (isMythicPack()) {
			return "%s (Mythic): %d".formatted(set, count);
		}
		// set code of Alchemy packs is e.g. Y22NEO, no extra check needed
		return "%s: %d".formatted(set, count);
	}

	/**
	 * @return true if this pack is an alchemy pack
	 */
	public boolean isAlchemyPack() {
		return collationId >= 400000 && collationId < 500000;
	}

	/**
	 * @return true if this pack is a mythic pack
	 */
	public boolean isMythicPack() {
		return collationId >= 500000 && collationId < 600000;
	}
}
