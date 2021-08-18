package hirsizlik.mtgacollection.bo;

/**
 * Interface for a card collection statistic.
 * @author Markus Schagerl
 *
 */
public interface Statistic{
	/**
	 * Checks if any card considered for this statistic is owned.
	 * @return true if at least one card is owned, otherwise false
	 */
	boolean isAnyCardOwned();
	/**
	 * Returns the amount of cards of that rarity currently owned.
	 * @param rarity the rarity
	 * @return the amount of cards of that rarity
	 */
	int getOwned(final Rarity rarity);
	/**
	 * Returns the amount of cards currently owned (over all rarities).
	 * @return the amount of cards owned
	 */
	int getOwnedTotal();
	/**
	 * Returns the total amount of cards in this statistic of that rarity.
	 * @param rarity the rarity
	 * @return the total amount of cards of that rarity
	 */
	int getTotal(Rarity rarity);
	/**
	 * Returns the total amount of cards in this statistic.
	 * @return the total amount of cards
	 */
	int getTotal();
	/**
	 * Returns the name of this statistic.
	 * @return the name
	 */
	String getName();
	/**
	 * Checks if this statistic has only cards in the standard format.
	 * @return true is the statistic considers only cards currently in standard.
	 */
	boolean isInStandard();

	/**
	 * Calculates the percentage of cards owned of a rarity.
	 * @param rarity the rarity for which this percentage is calculated
	 * @return the calculated percentage (0-100)
 	 */
	default double getPercentageOwned(final Rarity rarity) {
		return getOwned(rarity) / (double) getTotal(rarity) * 100d;
	}

	/**
	 * Calculates the percentage of all cards owned.
	 * @return the calculated percentage (0-100)
	 */
	default double getPercentageTotal() {
		return getOwnedTotal() / (double) getTotal() * 100d;
	}
}
