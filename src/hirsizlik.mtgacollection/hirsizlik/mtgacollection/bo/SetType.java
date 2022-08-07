package hirsizlik.mtgacollection.bo;

/**
 * Type of a MTGA Set
 *
 * @author Markus Schagerl
 */
public enum SetType {
	/** A Premier set (any set that is or was Standard legal) */
	PREMIER("P"),
	/** a Remastered set (Amonkhet Remastered, etc.)*/
	MASTER("M"),
	/** a Alchemy set */
	ALCHEMY("Y"),
	/** other supplemental sets (e.g. Modern Horizons, Jumpstart) */
	OTHER("O");

	private final String dbCode;

	private SetType(final String dbCode) {
		this.dbCode = dbCode;
	}

	/**
	 * @return the code used for this type
	 */
	public String getDbCode() {
		return dbCode;
	}

	/**
	 * Load the SetType corresponding to that code
	 * @param dbCode the database code
	 * @return the value
	 */
	public static SetType valueByCode(final String dbCode) {
		for (SetType s : SetType.values()) {
			if (s.dbCode.equals(dbCode)) {
				return s;
			}
		}
		throw new IllegalArgumentException("Unknown database code " + dbCode);
	}
}
