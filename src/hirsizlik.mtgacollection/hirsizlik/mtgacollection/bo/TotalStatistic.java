package hirsizlik.mtgacollection.bo;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Statistic over multiple statistics.
 * @author Markus Schagerl
 *
 */
public class TotalStatistic implements Statistic{

	private final String name;
	private final List<Statistic> statistics;

	/**
	 * Creates a TotalStatistic with the given name and list of statistics.
	 * @param name the name of this statistic
	 * @param statistics the sub statistics
	 */
	public TotalStatistic(final String name, final List<Statistic> statistics) {
		this.name = Objects.requireNonNull(name);
		this.statistics = Objects.requireNonNull(statistics);
	}

	@Override
	public boolean isAnyCardOwned() {
		return isAny(Statistic::isAnyCardOwned);
	}

	/**
	 * Calculates the sum of something over the statistics.
	 * @param toIntF the Function that extracts the value from the statistic to sum
	 * @return the sum of something
	 */
	private int getSum(final ToIntFunction<Statistic> toIntF) {
		return (int) statistics.stream().collect(Collectors.summarizingInt(toIntF)).getSum();
	}

	/**
	 * Checks if any statistic matches the given predicate
	 * @param sPredicate the statistic predicate
	 * @return true if any matches
	 */
	private boolean isAny(final Predicate<Statistic> sPredicate) {
		return statistics.stream().anyMatch(sPredicate);
	}

	@Override
	public int getOwned(final Rarity rarity) {
		return getSum(x -> x.getOwned(rarity));
	}

	@Override
	public int getOwnedTotal() {
		return getSum(Statistic::getOwnedTotal);
	}

	@Override
	public int getTotal(final Rarity rarity) {
		return getSum(x -> x.getTotal(rarity));
	}

	@Override
	public int getTotal() {
		return getSum(Statistic::getTotal);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isInStandard() {
		return !isAny(x -> !x.isInStandard());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TotalStatistic [name=");
		builder.append(name);
		builder.append(", statistics=");
		builder.append(statistics);
		builder.append("]");
		return builder.toString();
	}
}
