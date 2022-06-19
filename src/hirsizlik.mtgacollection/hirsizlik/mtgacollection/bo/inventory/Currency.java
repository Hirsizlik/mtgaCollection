package hirsizlik.mtgacollection.bo.inventory;

import java.util.List;

/**
 * Class for different currencies currently owned.
 *
 * @author Markus Schagerl
 */
public record Currency(
		/** amount of gold*/
		int gold,
		/** amount of gems */
		int gems,
		/** amount of (traditional) draft tokens */
		List<Token> tokens) {}
