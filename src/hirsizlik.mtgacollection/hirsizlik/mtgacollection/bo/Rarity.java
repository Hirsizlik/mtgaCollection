package hirsizlik.mtgacollection.bo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Information about card rarities.
 *
 * @author Markus Schagerl
 *
 */
public enum Rarity {
	/** Rarity used for basic lands*/
	LAND("land", "L", 1),
	/** Common rarity (black set symbol)*/
	COMMON("common", "C", 2),
	/** Uncommon rarity (silver set symbol) */
	UNCOMMON("uncommon", "U", 3),
	/** Rare rarity (gold set symbol) */
	RARE("rare", "R", 4),
	/** Mythic rare rarity (red set symbol) */
	MYTHIC("mythic", "M", 5);

	private final String name;
	private final String dbCode;
	private final int mtgaCode;

	private static final List<Rarity> COMMON_TO_MYTHIC_LIST =
			List.of(Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.MYTHIC);

	private Rarity(final String name, final String dbCode, final int mtgaCode){
		this.name = name;
		this.dbCode = dbCode;
		this.mtgaCode = mtgaCode;
	}

	/**
	 * @return The name of this rarity
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The code used in the Database
	 */
	public String getDbCode() {
		return dbCode;
	}

	/**
	 * @return a new stream of COMMON, UNCOMMON, RARE and MYTHIC.
	 */
	public static Stream<Rarity> getCommonToMythicStream(){
		return COMMON_TO_MYTHIC_LIST.stream();
	}

	/**
	 * Gets the value with the given database code.
	 *
	 * @param code the database code
	 * @return the Rarity with that code
	 * @throws IllegalArgumentException if no Rarity for that code was found
	 */
	public static Rarity valueByCode(final String code) {
		return firstValueByFilter(x -> x.dbCode.equals(code), code);
	}

	/**
	 * Gets the value with the given code used by MTGA.
	 *
	 * @param code the MTGA code
	 * @return the Rarity with that code
	 * @throws IllegalArgumentException if no Rarity for that code was found
	 */
	public static Rarity valueByMtgaCode(final int code) {
		return firstValueByFilter(x -> x.mtgaCode == code, code);
	}

	private static Rarity firstValueByFilter(final Predicate<Rarity> filter, final Object valueForLogging) {
		return Arrays.asList(Rarity.values())
				.stream()
				.filter(filter)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no such rarity found with " + valueForLogging));
	}
}
