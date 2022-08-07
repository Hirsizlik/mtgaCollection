package hirsizlik.mtgacollection.scryfall;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Certain Sets from Scryfall and MTGA don't match or are missing. This class adds workarounds for those.
 *
 * @author Markus Schagerl
 */
public final class ScryfallSetQuirk {

	private ScryfallSetQuirk() {}

	/**
	 * Translates set codes in Arena to the ones used in Scryfall to find them.
	 *
	 * @param mtgaCode the arena setcode
	 * @return the setcode for Scryfall
	 */
	public static String translateToScryfall(final String mtgaCode) {
		return switch (mtgaCode) {
		case "CONF" -> "CON";
		case "Y22-NEO" -> "YNEO";
		case "Y22-MID" -> "YMID";
		case "Y22-SNC" -> "YSNC";
		case "AHA1" -> "HA1";
		case "AHA2" -> "HA2";
		case "AHA3" -> "HA3";
		case "AHA4" -> "HA4";
		case "AHA5" -> "HA5";
		case "AHA6" -> "HA6";
		default -> mtgaCode;
		};
	}

	/**
	 * Translate the set code from Scryfall to the one actually used in Arena.
	 *
	 * @param scryfallArenaCode the "arena_code" from Scryfall
	 * @return the actual Arena code
	 */
	public static String translateToMtga(final String scryfallArenaCode) {
		return switch (scryfallArenaCode) {
		case "MI" -> "MIR";
		case "WL" -> "WTH";
		case "MM" -> "MMQ";
		case "IN" -> "INV";
		case "PS" -> "PLS";
		case "OD" -> "ODY";
		case "CON" -> "CONF";// those translated in translateToScryfall have to be translated back to MTGA codes
		case "YNEO" -> "Y22-NEO";
		case "YMID" -> "Y22-MID";
		case "YSNC" -> "Y22-SNC";
		case "HA1" -> "AHA1";
		case "HA2" -> "AHA2";
		case "HA3" -> "AHA3";
		case "HA4" -> "AHA4";
		case "HA5" -> "AHA5";
		case "HA6" -> "AHA6";
		default -> scryfallArenaCode;
		};
	}

	/**
	 * Creates a fake Scryfall-Set for certain Sets that are not in Scryfall but in Arena.
	 *
	 * @param mtgaCode the Arena set code
	 * @return the fake set for this code, if needed, otherwise empty
	 */
	public static Optional<ScryfallSetInfo> createFakeScryfallSet(final String mtgaCode) {
		if ("ArenaSUP".equals(mtgaCode)) {
			return Optional.of(new ScryfallSetInfo("Arena Supplemental (unused)", "ArenaSUP", ScryfallSetType.FUNNY,
					LocalDate.of(1970, 1, 1)));
		}

		if ("Y22".equals(mtgaCode)) {
			// Scryfall used YMID, YNEO and YSNC instead
			return Optional.of(new ScryfallSetInfo("Alchemy 2022", "Y22", ScryfallSetType.ALCHEMY,
					LocalDate.of(2021, 12, 9)));
		}

		if ("BC20".equals(mtgaCode)) {
			// Rhys the Redeemed and the other Brawl commanders used this as DigitalReleaseSet
			return Optional.of(new ScryfallSetInfo("Brawl Commander", "BC20", ScryfallSetType.PROMO,
					LocalDate.of(2019, 12, 12)));
		}

		return Optional.empty();
	}
}
