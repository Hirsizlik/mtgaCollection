package hirsizlik.mtgacollection.mapper;

import java.time.LocalDate;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hirsizlik.mtgacollection.jackson.scryfall.set.ScryfallSet;
import hirsizlik.mtgacollection.scryfall.ScryfallSetInfo;
import hirsizlik.mtgacollection.scryfall.ScryfallSetQuirk;
import hirsizlik.mtgacollection.scryfall.ScryfallSetType;

/**
 * Maps the data from Scryfall (using a generated class) into a internally used class.
 * Doesn't use SetInfo as those represent sets from the database.
 *
 * @author Markus Schagerl
 * @see ScryfallSet ScryfallSet (from JSON generated class)
 * @see ScryfallSetInfo ScryfallSetInfo (internal class)
 */
public class MapScryfallSet implements Mapper<ScryfallSet, ScryfallSetInfo> {

	private static final Logger LOG = LogManager.getLogger();

	@Override
	public MappingResult<ScryfallSet, ScryfallSetInfo> apply(final ScryfallSet scryfallSet) {
		return MappingResult.createOk(scryfallSet, new ScryfallSetInfo(
				scryfallSet.getName(),
				getSetCode(scryfallSet),
				ScryfallSetType.valueOf(scryfallSet.getSetType().toUpperCase(Locale.ENGLISH)),
				LocalDate.parse(scryfallSet.getReleasedAt())));
	}

	private static String getSetCode(final ScryfallSet scryfallSet) {
		if(scryfallSet.getArenaCode() != null) {
			return ScryfallSetQuirk.translateToMtga(scryfallSet.getArenaCode().toUpperCase(Locale.ENGLISH));
		} else {
			// SLD (Secret Lair Drops), Arena-only sets (ANA, ANB, AKR, KLR, J21),
			// JMP (Jumpstart), G18 (M19 Gift Pack), UND (Unsanctioned) and UST (Unstable)
			LOG.warn("arena_code is null, use code '{}' instead.", scryfallSet.getCode());
			return scryfallSet.getCode().toUpperCase(Locale.ENGLISH);
		}
	}
}
