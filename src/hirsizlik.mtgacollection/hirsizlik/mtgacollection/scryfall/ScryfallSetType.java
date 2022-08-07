package hirsizlik.mtgacollection.scryfall;

import hirsizlik.mtgacollection.bo.SetType;

/**
 * Set Types from Scryfall.
 *
 * @author Markus Schagerl
 */
public enum ScryfallSetType {
	/** A yearly Magic core set (Tenth Edition, etc) */
	CORE(SetType.PREMIER),
	/** A rotational expansion set in a block (Zendikar, etc) */
	EXPANSION(SetType.PREMIER),
	/** A reprint set that contains no new cards (Modern Masters, etc) */
	MASTERS(SetType.MASTER),
	/** An Arena set designed for Alchemy */
	ALCHEMY(SetType.ALCHEMY),
	/** Masterpiece Series premium foil cards */
	MASTERPIECE(SetType.OTHER),
	/** A Commander-oriented gift set */
	ARSENAL(SetType.OTHER),
	/** From the Vault gift sets */
	FROM_THE_VAULT(SetType.OTHER),
	/** Spellbook series gift sets */
	SPELLBOOK(SetType.OTHER),
	/** Premium Deck Series decks */
	PREMIUM_DECK(SetType.OTHER),
	/** Duel Decks */
	DUEL_DECK(SetType.OTHER),
	/** Special draft sets, like Conspiracy and Battlebond */
	DRAFT_INNOVATION(SetType.OTHER),
	/** Magic Online treasure chest prize sets */
	TREASURE_CHEST(SetType.OTHER),
	/** Commander preconstructed decks */
	COMMANDER(SetType.OTHER),
	/** Planechase sets */
	PLANECHASE(SetType.OTHER),
	/** Archenemy sets */
	ARCHENEMY(SetType.OTHER),
	/** Vanguard card sets */
	VANGUARD(SetType.OTHER),
	/** A funny un-set or set with funny promos (Unglued, Happy Holidays, etc) */
	FUNNY(SetType.OTHER),
	/** A starter/introductory set (Portal, etc) */
	STARTER(SetType.OTHER),
	/** A gift box set */
	BOX(SetType.OTHER),
	/** A set that contains purely promotional cards */
	PROMO(SetType.OTHER),
	/** A set made up of tokens and emblems. */
	TOKEN(SetType.OTHER),
	/** A set made up of gold-bordered, oversize, or trophy cards that are not legal */
	MEMORABILIA(SetType.OTHER);

	private final SetType internalSetType;

	private ScryfallSetType(final SetType internalSetType) {
		this.internalSetType = internalSetType;
	}

	/**
	 * @return the corresponding internal set type
	 */
	public SetType getInternalSetType() {
		return internalSetType;
	}
}
