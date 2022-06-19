package hirsizlik.mtgacollection.mapper;

import java.util.List;

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
public class MapMtgaInventoryToInventory implements Mapper<PlayerInventory, Inventory>{

	@Override
	public MappingResult<PlayerInventory, Inventory> apply(final PlayerInventory t) {
		return MappingResult.createOk(t,
			new Inventory(
				new Wildcard(
					t.getWildCardCommons(),
					t.getWildCardUnCommons(),
					t.getWildCardRares(),
					t.getWildCardMythics(),
					t.getWcTrackPosition()),
				new Currency(
					t.getGold(),
					t.getGems(),
					mapTokens(t.getCustomTokens())),
				t.getTotalVaultProgress(),
				mapBooster(t.getBoosters())
			)
		);
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
