package hirsizlik.mtgacollection.scryfall;

/**
 * Set Types from Scryfall.
 *
 * @author Markus Schagerl
 */
public enum ScryfallSetType {
	/** A yearly Magic core set (Tenth Edition, etc) */
	CORE(false),
	/** A rotational expansion set in a block (Zendikar, etc) */
	EXPANSION(false),
	/** A reprint set that contains no new cards (Modern Masters, etc) */
	MASTERS(true),
	/** An Arena set designed for Alchemy */
	ALCHEMY(true),
	/** Masterpiece Series premium foil cards */
	MASTERPIECE(true),
	/** A Commander-oriented gift set */
	ARSENAL(true),
	/** From the Vault gift sets */
	FROM_THE_VAULT(true),
	/** Spellbook series gift sets */
	SPELLBOOK(true),
	/** Premium Deck Series decks */
	PREMIUM_DECK(true),
	/** Duel Decks */
	DUEL_DECK(true),
	/** Special draft sets, like Conspiracy and Battlebond */
	DRAFT_INNOVATION(true),
	/** Magic Online treasure chest prize sets */
	TREASURE_CHEST(true),
	/** Commander preconstructed decks */
	COMMANDER(true),
	/** Planechase sets */
	PLANECHASE(true),
	/** Archenemy sets */
	ARCHENEMY(true),
	/** Vanguard card sets */
	VANGUARD(true),
	/** A funny un-set or set with funny promos (Unglued, Happy Holidays, etc) */
	FUNNY(true),
	/** A starter/introductory set (Portal, etc) */
	STARTER(true),
	/** A gift box set */
	BOX(true),
	/** A set that contains purely promotional cards */
	PROMO(true),
	/** A set made up of tokens and emblems. */
	TOKEN(true),
	/** A set made up of gold-bordered, oversize, or trophy cards that are not legal */
	MEMORABILIA(true);

	private final boolean isSupplemental;

	private ScryfallSetType(final boolean isSupplemental) {
		this.isSupplemental = isSupplemental;
	}

	public boolean isSupplemental() {
		return isSupplemental;
	}
}
