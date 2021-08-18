package hirsizlik.mtgacollection.bo.inventory;

/**
 * A VanityItem that isn't a {@link Pet} (a avatar or a cardback).
 * @author Markus Schagerl
 */
public record VanityItem(
		/** the name of this item */
		String name) {

}
