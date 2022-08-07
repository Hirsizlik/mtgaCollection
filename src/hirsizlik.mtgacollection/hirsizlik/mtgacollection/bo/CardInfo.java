package hirsizlik.mtgacollection.bo;

import java.util.Optional;

/**
 * Information about a card from a specific set.
 *
 * @author Markus Schagerl
 */
public record CardInfo(int id, String name, Rarity rarity, SetInfo set, boolean inBooster,
		Optional<SetInfo> digitalSet, boolean rebalanced) {}
