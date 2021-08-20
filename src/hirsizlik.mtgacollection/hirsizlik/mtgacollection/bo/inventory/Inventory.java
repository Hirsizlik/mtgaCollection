package hirsizlik.mtgacollection.bo.inventory;

import java.util.List;
import java.util.stream.Collectors;

import hirsizlik.mtgacollection.bo.Rarity;

/**
 * Describes the inventory excluding cards. That includes the playerId, any
 * currencies, booster, and vanity stuff.
 *
 * @author Markus Schagerl
 */
public record Inventory(
		/** The MTGA playerId */
		String playerId,
		/** Wildcards and the current trackPosition */
		Wildcard wildcard,
		/** All Currencies (gold, gems, tokens)*/
		Currency currency,
		/** Current VaultProgress*/
		double vaultProgress,
		/** All kind of booster you currently have*/
		List<Booster> boosters,
		/** All vouchers (currently unimplemented) */
		List<?> vouchers,
		/** The vanity stuff (pets, avatar, cardbacks)*/
		Vanity vanity) {

	/**
	 * returns a formatted string describing this inventory.
	 *
	 * @return the formatted string, contains information about wildcards,
	 *         currencies, vault progress and booster. Ends with a "---" and a new
	 *         line.
	 */
	public String asFormattedString(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Wildcard: %d C, %d U, %d R, %d M%n",
				wildcard.getAmountOf(Rarity.COMMON),
				wildcard.getAmountOf(Rarity.UNCOMMON),
				wildcard.getAmountOf(Rarity.RARE),
				wildcard.getAmountOf(Rarity.MYTHIC)));

		appendCurrencies(sb);
		sb.append(String.format("Vault progress: %.1f%%%n", vaultProgress));

		if (!boosters.isEmpty()) {
			sb.append(boosters.stream()
					.map(Booster::getShortString)
					.collect(Collectors.joining(", ", "Booster: ", System.lineSeparator())));
		}

		sb.append("---").append(System.lineSeparator());
		return sb.toString();
	}

	private void appendCurrencies(final StringBuilder sb) {
		Currency c = currency;

		sb.append(String.format("Currencies: %d gold, %d gems%n",
				c.gold(), c.gems()));
		if(c.hasDraftOrSealedTokens()) {
			sb.append(String.format("Tokens: %d Draft, %d Sealed%n",
					c.draftTokens(),
					c.sealedTokens()));
		}
	}

}
