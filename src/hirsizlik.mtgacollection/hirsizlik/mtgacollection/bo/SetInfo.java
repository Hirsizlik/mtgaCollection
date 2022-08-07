package hirsizlik.mtgacollection.bo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class holding information about a set.
 *
 * @author Markus Schagerl
 */
public record SetInfo(
		/** the 3-letter set code */
		String code,
		/** the name of the set */
		String name,
		/** the amount of cards in the set by rarity */
		CardAmount cardAmount,
		/** the release date*/
		LocalDate release,
		/** the set type (Premier, Master, etc.) */
		SetType type) {

	/**
	 * Checks if the set is released before a certain date
	 *
	 * @param date the date to check, may not be null
	 * @return true if the set is released before the given date
	 */
	public boolean isReleasedBefore(final LocalDate date) {
		return release.isBefore(Objects.requireNonNull(date));
	}

}
