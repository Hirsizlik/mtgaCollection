package hirsizlik.mtgacollection.bo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Holds statistics for a single set.
 *
 * @author Markus Schagerl
 */
public class SetStatistic implements Statistic {

	private static final LocalDate RELEASE_RETURN_TO_RAVNICA = LocalDate.of(2012, 10, 5);
	private static final Set<String> ANA_ANB = Set.of("ANA", "ANB");
	private static final int MAX_COPIES = 4;

	private final SetInfo set;
	private final Map<Rarity, AtomicInteger> ownedByRarity;
	private final int totalOwned;
	private final LocalDate standardStart;

	/**
	 * Creates a new object. The data is read and prepared while doing so.
	 *
	 * @param set the set a statistic is created for. The name of that set is used as name for this statistic.
	 * @param cards the list of cards
	 * @param cardsOwned a map with key = cardId and value = amount of cards
	 * @param standardStart the date with which the current standards starts
	 */
	public SetStatistic(final SetInfo set, final List<CardInfo> cards, final Map<Integer, Integer> cardsOwned,
			final LocalDate standardStart) {
		this.set = set;
		ownedByRarity = new EnumMap<>(Rarity.class);
		var rarityInBoosterMap = cards.stream()
				.filter(CardInfo::inBooster)
				.filter(c -> c.set() == set)
				.collect(Collectors.groupingBy(CardInfo::rarity));

		Arrays.asList(Rarity.values()).forEach(r -> {
			ownedByRarity.putIfAbsent(r, new AtomicInteger());

			rarityInBoosterMap.getOrDefault(r, Collections.emptyList()).forEach(c -> {
				Optional<Integer> i = Optional.ofNullable(cardsOwned.get(c.id()));
				ownedByRarity.get(r).addAndGet(i.orElse(0));
			});
		});

		totalOwned = ownedByRarity.entrySet().stream()
				.filter(x -> x.getKey() != Rarity.LAND)
				.mapToInt(x -> x.getValue().get())
				.sum();

		this.standardStart = standardStart;
	}

	@Override
	public boolean isAnyCardOwned() {
		return totalOwned > 0;
	}

	@Override
	public int getOwned(final Rarity rarity) {
		return ownedByRarity.get(rarity).get();
	}

	@Override
	public int getOwnedTotal() {
		return totalOwned;
	}

	@Override
	public boolean isInStandard() {
		return (set.type() == SetType.PREMIER && !set.isReleasedBefore(standardStart));
	}

	@Override
	public boolean isInAlchemy() {
		boolean isAlchemyOrPremierSet = set.type() == SetType.PREMIER || set.type() == SetType.ALCHEMY;
		return ANA_ANB.contains(set.code()) || (!set.isReleasedBefore(standardStart) && isAlchemyOrPremierSet);
	}

	@Override
	public boolean isInPioneer() {
		// doesn't consider remastered sets as those also contain non-Pioneer cards
		return set.type() == SetType.PREMIER && !set.isReleasedBefore(RELEASE_RETURN_TO_RAVNICA);
	}

	@Override
	public int getTotal(final Rarity rarity) {
		return set.cardAmount().getAmountOfRarity(rarity) * MAX_COPIES;
	}

	@Override
	public int getTotal() {
		return set.cardAmount().total() * MAX_COPIES;
	}

	@Override
	public String getName() {
		return "[%s] %s".formatted(set.code(), set.name());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetStatistic [set=");
		builder.append(set);
		builder.append(", ownedByRarity=");
		builder.append(ownedByRarity);
		builder.append(", totalOwned=");
		builder.append(totalOwned);
		builder.append(", standardStart=");
		builder.append(standardStart);
		builder.append("]");
		return builder.toString();
	}

}
