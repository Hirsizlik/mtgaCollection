package hirsizlik.mtgacollection.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hirsizlik.mtgacollection.bo.SetInfo;

/**
 * Manages Sets, allowing them to be searched by Code.
 *
 * @author Markus Schagerl
 */
public class SetInfoLoader {
	private final Map<String, SetInfo> setMapByCode = new HashMap<>();
	private final List<SetInfo> allSets = new ArrayList<>();
	private final SetInfo unknown;

	/**
	 * Constructs this loader using the data from the database.
	 * Prepares the data for easier searching.
	 *
	 * @param setInfoMapFromDb sets from database
	 */
	public SetInfoLoader(final Map<String, SetInfo> setInfoMapFromDb) {
		unknown = setInfoMapFromDb.get("?");
		setMapByCode.putAll(setInfoMapFromDb);
		allSets.addAll(setInfoMapFromDb.values());
		Collections.sort(allSets, Comparator.comparing(SetInfo::release));
	}

	/**
	 * Returns the Set with the specified code, or the unknown is if no such set was found.
	 *
	 * @param code the setcode (usually a 3-letter-code).
	 * @return the SetInfo with that code, or the unknown set
	 */
	public SetInfo getByCode(final String code) {
		return setMapByCode.getOrDefault(code, unknown);
	}

	/**
	 * @return a list with all sets in the database (including the unknown set)
	 */
	public List<SetInfo> getAllSets(){
		return allSets;
	}

	/**
	 * @return the unknown set, a auxiliary set used if a set wasn't found.
	 * It is a singleton, so comparison with "==" can be used.
	 */
	public SetInfo getUnknownSet() {
		return unknown;
	}

}
