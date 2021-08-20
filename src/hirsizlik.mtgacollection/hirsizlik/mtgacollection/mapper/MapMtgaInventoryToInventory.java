package hirsizlik.mtgacollection.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hirsizlik.mtgacollection.bo.inventory.Booster;
import hirsizlik.mtgacollection.bo.inventory.Currency;
import hirsizlik.mtgacollection.bo.inventory.Inventory;
import hirsizlik.mtgacollection.bo.inventory.Pet;
import hirsizlik.mtgacollection.bo.inventory.Vanity;
import hirsizlik.mtgacollection.bo.inventory.VanityItem;
import hirsizlik.mtgacollection.bo.inventory.VanityItems;
import hirsizlik.mtgacollection.bo.inventory.VanitySelections;
import hirsizlik.mtgacollection.bo.inventory.Wildcard;
import hirsizlik.mtgacollection.jackson.inventory.Payload;
import hirsizlik.mtgacollection.jackson.inventory.PlayerInventory;

/**
 * Maps the raw inventory data (json from logs) to a more manageable class.
 *
 * @author Markus Schagerl
 */
public class MapMtgaInventoryToInventory implements Mapper<PlayerInventory, Inventory>{

	@Override
	public MappingResult<PlayerInventory, Inventory> apply(final PlayerInventory t) {
		Payload p = t.getPayload();

		VanityItems vanityItems = mapVanityItems(p.getVanityItems());
		VanitySelections vanitySelections = mapVanitySelections(p.getVanitySelections(), vanityItems);
		return MappingResult.createOk(t,
				new Inventory(p.getPlayerId(),
						new Wildcard(
								p.getWcCommon(),
								p.getWcUncommon(),
								p.getWcRare(),
								p.getWcMythic(),
								p.getWcTrackPosition()),
						new Currency(
								p.getGold(),
								p.getGems(),
								p.getDraftTokens(),
								p.getSealedTokens()),
								p.getVaultProgress(),
						mapBooster(p.getBoosters()),
						p.getVouchers(), // Voucher -> presale stuff (e.g. 50 Packs Bundle)
						new Vanity(
								vanityItems,
								vanitySelections,
								p.getBasicLandSet())));
	}

	private List<Booster> mapBooster(final List<hirsizlik.mtgacollection.jackson.inventory.Booster> booster){
		return booster.stream().map(b ->
			new Booster(b.getCount(), b.getCollationId()))
				.collect(Collectors.toList());
	}

	private VanityItems mapVanityItems(final hirsizlik.mtgacollection.jackson.inventory.VanityItems vanityItems){
		List<Pet> pets = vanityItems.getPets().stream().map(p -> new Pet(p.getName(), p.getVariants()))
				.collect(Collectors.toList());
		List<VanityItem> avatars = vanityItems.getAvatars().stream().map(a -> new VanityItem(a.getName()))
				.collect(Collectors.toList());
		List<VanityItem> cardBacks = vanityItems.getCardBacks().stream().map(cb -> new VanityItem(cb.getName()))
				.collect(Collectors.toList());

		return new VanityItems(pets, avatars, cardBacks);
	}

	private VanitySelections mapVanitySelections(
			final hirsizlik.mtgacollection.jackson.inventory.VanitySelections vanitySelections,
			final VanityItems vanityItems) {
		Optional<VanityItem> avatar = vanityItems.avatars().stream()
				.filter(a -> a.name().equals(vanitySelections.getAvatarSelection())).findAny();

		Optional<Pet> pet;
		if(vanitySelections.getPetSelection() != null) { // PetSelection is null if no pet is selected
			pet = vanityItems.pets().stream()
					.filter(p -> p.name().equals(vanitySelections.getPetSelection().getName())).findAny();
		}else {
			pet = Optional.empty();
		}

		return new VanitySelections(avatar, Optional.empty(), pet);
	}

}
