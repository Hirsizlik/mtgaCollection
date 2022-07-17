package hirsizlik.mtgacollection.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import hirsizlik.mtgacollection.bo.SetInfo;

/**
 * Manages Sets, allowing them to be searched by Code.
 *
 * @author Markus Schagerl
 */
public class SetInfoLoader {
	private final Map<String, SetInfo> setMapByCode = new HashMap<>();
	private final List<SetInfo> allSets = new ArrayList<>();

	/**
	 * Constructs this loader using the data from the database.
	 * Prepares the data for easier searching.
	 *
	 * @param setInfoMapFromDb sets from database
	 */
	public SetInfoLoader(final Map<String, SetInfo> setInfoMapFromDb) {
		setMapByCode.putAll(setInfoMapFromDb);
		allSets.addAll(setInfoMapFromDb.values());
		Collections.sort(allSets, Comparator.comparing(SetInfo::release));
	}

	/**
	 * Returns the Set with the specified code.
	 *
	 * @param code the set code (usually a 3-letter-code).
	 * @return the SetInfo with that code
	 * @throws NullPointerException if no set with that code was found
	 */
	public SetInfo getByCode(final String code) {
		return Objects.requireNonNull(setMapByCode.get(code));
	}

	/**
	 * @return a list with all sets in the database, ordered by release
	 */
	public List<SetInfo> getAllSets(){
		return allSets;
	}

}
