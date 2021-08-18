package hirsizlik.mtgacollection.bo.inventory;

/**
 * Class for different currencies currently owned.
 * @author Markus Schagerl
 *
 */
public record Currency(
		/** amount of gold*/
		int gold,
		/** amount of gems */
		int gems,
		/** amount of (traditional) draft tokens */
		int draftTokens,
		/** amount of sealed tokens*/
		int sealedTokens) {

	/**
	 * Checks if any tokens (draft + sealed) are currently owned.
	 * @return true if any token is owned, otherwise false
	 */
	public boolean hasDraftOrSealedTokens() {
		return draftTokens > 0 || sealedTokens > 0;
	}

}
