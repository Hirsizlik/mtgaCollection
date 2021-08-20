package hirsizlik.mtgacollection.bo;

/**
 * Class for the amount of cards by rarity plus total in a set.
 *
 * @author Markus Schagerl
 * @see SetInfo
 */
public record CardAmount(int commons, int uncommons, int rares, int mythics) {

	/**
	 * Returns the amount of cards of that rarity.
	 *
	 * @param r the rarity to check
	 * @return the amount of cards with that rarity
	 */
	public int getAmountOfRarity(final Rarity r) {
		return switch (r) {
		case COMMON -> commons;
		case UNCOMMON -> uncommons;
		case RARE -> rares;
		case MYTHIC -> mythics;
		default -> 0;
		};
	}

	/**
	 * @return total amount of cards (sum of all rarities)
	 */
	public int total() {
		return commons + uncommons + rares + mythics;
	}
}
