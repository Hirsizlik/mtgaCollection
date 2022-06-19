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
		/** Wildcards and the current trackPosition */
		Wildcard wildcard,
		/** All Currencies (gold, gems, tokens)*/
		Currency currency,
		/** Current VaultProgress*/
		int vaultProgress,
		/** All kind of booster you currently have*/
		List<Booster> boosters) {

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
		sb.append(String.format("Vault progress: %.1f%%%n", vaultProgress / 10f));

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
		if(!c.tokens().isEmpty()) {
			String tokens = c.tokens().stream()
					.map(t -> String.format("%s: %d", t.name(), t.count()))
					.collect(Collectors.joining(",", "Tokens: [", "]%n".formatted()));
			sb.append(tokens);
		}
	}

}
