package hirsizlik.mtgacollection.bo.inventory;

/**
 * A draft, sealed or whatever token
 *
 * @author Markus Schagerl
 */
public record Token(
		/** Name of the token ("DraftToken", etc.) */
		String name,
		/** The amount of those tokens */
		int count) {}
