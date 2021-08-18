package hirsizlik.mtgacollection.scryfall;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Certain Sets from Scryfall and MTGA don't match or are missing. This class adds workarounds for those.
 * @author Markus Schagerl
 *
 */
public final class ScryfallSetQuirk {

	private ScryfallSetQuirk() {}

	/**
	 * Translates set codes in Arena which are not found in Scryfall.
	 * Currently only "CONF" (Conflux) needs to be translated to "CON"
	 * @param mtgaCode the arena setcode
	 * @return the setcode for Scryfall
	 */
	public static String translateToScryfall(final String mtgaCode) {
		return switch (mtgaCode) {
		case "CONF" -> "CON";
		default -> mtgaCode;
		};
	}

	/**
	 * Translate the set code from Scryfall to the one actually used in Arena.
	 * @param scryfallArenaCode the "arena_code" from Scryfall
	 * @return the actual Arena code
	 */
	public static String translateToMtga(final String scryfallArenaCode) {
		return switch (scryfallArenaCode) {
		case "CON" -> "CONF";
		case "MI" -> "MIR";
		case "WL" -> "WTH";
		case "MM" -> "MMQ";
		case "IN" -> "INV";
		case "PS" -> "PLS";
		case "OD" -> "ODY";
		default -> scryfallArenaCode;
		};
	}

	/**
	 * Creates a fake Scryfall-Set for certain Sets that are not in Scryfall but in Arena.
	 * @param mtgaCode the Arena set code
	 * @return the fake set for this code, if needed, otherwise empty
	 */
	public static Optional<ScryfallSetInfo> createFakeScryfallSet(final String mtgaCode) {
		if("ArenaSUP".equals(mtgaCode)) {
			return Optional.of(new ScryfallSetInfo("Arena Supplemental (unused)", "ArenaSUP", ScryfallSetType.FUNNY,
					LocalDate.of(1970, 1, 1)));
		}

		if("ANC".equals(mtgaCode)) {
			return Optional.of(new ScryfallSetInfo("Arena Mirror Mirror", "ANC", ScryfallSetType.FUNNY,
					LocalDate.of(2021, 7, 3)));
		}

		return Optional.empty();
	}
}
