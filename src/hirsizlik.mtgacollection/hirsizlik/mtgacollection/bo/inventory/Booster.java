package hirsizlik.mtgacollection.bo.inventory;

/**
 * Describes the amount of booster of a set currently owned.
 *
 * @author Markus Schagerl
 */
public record Booster(
		/** the amount of booster packs currently owned*/
		int count,
		/** the collationId, can be mapped to set code */
		Integer collationId) {

	/**
	 * @return a short string describing this booster.
	 * <div>
	 * Format is {@code <set code>: <amount of booster>}"
	 * </div>
	 */
	public String getShortString() {
		return "%s: %d".formatted(getSetCodeFromCollation(), count);
	}

	// can't be imported from the game as it is hard coded there, see "CollationMapping"-class
	// Note: collationId of draft packs start with 2, cubes start wit 3. both not included here
	/**
	 * Returns the set code corresponding to this packs collationId.
	 *
	 * @return the set code of this booster, or it's collationId if unknown.
	 */
	public String getSetCodeFromCollation() {
		return switch(collationId) {
		case 62242 -> "KLD";
		case 62979 -> "AER";
		case 100003 -> "AKH";
		case 100004 -> "HOU";
		case 100005 -> "XLN";
		case 100006 -> "RIX";
		case 100007 -> "DAR";
		case 100008 -> "M19";
		case 100009 -> "GRN";
		case 100010 -> "RNA";
		case 100013 -> "WAR";
		case 100014 -> "M20";
		case 100015 -> "ELD";
		case 100016 -> "THB";
		case 100017 -> "IKO";
		case 100018 -> "M21";
		case 100019 -> "AKR";
		case 100020 -> "ZNR";
		case 100021 -> "KLR";
		case 100022 -> "KHM";
		case 100023 -> "STX";
		case 100024 -> "AFR";
		default -> collationId.toString();
		};
	}
}
