package hirsizlik.mtgacollection.bo;

/**
 * Information about a card from a specific set.
 *
 * @author Markus Schagerl
 */
public record CardInfo(int id, String name, Rarity rarity, SetInfo set, boolean inBooster) {}
