package hirsizlik.mtgacollection.bo.inventory;

import java.util.List;

/**
 * A pet and its variants (levels)
 *
 * @author Markus Schagerl
 */
public record Pet(
		/** the pet name*/
		String name,
		/** it's variants */
		List<String> variants){

}
