package hirsizlik.mtgacollection.mapper;

import java.util.List;
import java.util.function.Function;

import hirsizlik.mtgacollection.bo.inventory.Booster;
import hirsizlik.mtgacollection.bo.inventory.Currency;
import hirsizlik.mtgacollection.bo.inventory.Inventory;
import hirsizlik.mtgacollection.bo.inventory.Token;
import hirsizlik.mtgacollection.bo.inventory.Wildcard;
import hirsizlik.mtgacollection.jackson.inventory.CustomToken;
import hirsizlik.mtgacollection.jackson.inventory.PlayerInventory;

/**
 * Maps the raw inventory data (json from logs) to a more manageable class.
 *
 * @author Markus Schagerl
 */
public class MapMtgaInventoryToInventory implements Function<PlayerInventory, Inventory>{

	@Override
	public Inventory apply(final PlayerInventory t) {
		return new Inventory(
			new Wildcard(
				defaultZero(t.getWildCardCommons()),
				defaultZero(t.getWildCardUnCommons()),
				defaultZero(t.getWildCardRares()),
				defaultZero(t.getWildCardMythics()),
				defaultZero(t.getWcTrackPosition())),
			new Currency(
				defaultZero(t.getGold()),
				defaultZero(t.getGems()),
				mapTokens(t.getCustomTokens())),
			defaultZero(t.getTotalVaultProgress()),
			mapBooster(t.getBoosters())
		);
	}

	private static int defaultZero(final Integer i) {
		return i != null ? i : 0;
	}

	private List<Booster> mapBooster(final List<hirsizlik.mtgacollection.jackson.inventory.Booster> boosters){
		return boosters.stream()
			.map(b ->new Booster(b.getCount(), b.getCollationId(), b.getSetCode()))
			.toList();
	}

	private List<Token> mapTokens(final CustomToken tokens) {
		return tokens.getAdditionalProperties().entrySet().stream()
				.map(e -> new Token(e.getKey(), (Integer) e.getValue()))
				.toList();
	}

}
