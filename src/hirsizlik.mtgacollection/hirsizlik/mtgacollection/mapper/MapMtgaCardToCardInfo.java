package hirsizlik.mtgacollection.mapper;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

import hirsizlik.mtgacollection.bo.CardInfo;
import hirsizlik.mtgacollection.bo.Rarity;
import hirsizlik.mtgacollection.bo.SetInfo;
import hirsizlik.mtgacollection.database.RawCardDatabaseDAO;
import hirsizlik.mtgacollection.database.SetInfoLoader;
import hirsizlik.mtgacollection.jackson.mtga.card.MtgaCard;

/**
 * Maps a card from the MTGA json files to a card used in this program.
 *
 * @author Markus Schagerl
 */
public class MapMtgaCardToCardInfo implements Function<MtgaCard, Optional<CardInfo>>{

	private final RawCardDatabaseDAO rawCardDatabaseDAO;
	private final SetInfoLoader sil;

	// 1 to 3 digits
	private static final Pattern COLLECTOR_NUMBER_PATTERN = Pattern.compile("\\d?\\d?\\d");
	// special art Llanovar elves, Firemind's research, Duress, Ghalta, Primal Hunger
	private static final List<Integer> SPECIAL_ART_CARDS = List.of(69781, 69780, 70141, 70140);

	/**
	 * Creates the Mapper.
	 *
	 * @param rawCardDatabaseDAO access to the Localization database.
	 * @param setInfoLoader to load the set for a card.
	 */
	public MapMtgaCardToCardInfo(final RawCardDatabaseDAO rawCardDatabaseDAO, final SetInfoLoader setInfoLoader) {
		this.rawCardDatabaseDAO = rawCardDatabaseDAO;
		this.sil = setInfoLoader;
	}

	@Override
	public Optional<CardInfo> apply(final MtgaCard mtgaCard) {
		if (Boolean.TRUE.equals(mtgaCard.getIsToken()) || Boolean.TRUE.equals(mtgaCard.getIsSecondaryCard())) {
			// token or half a split card, do not map
			return Optional.empty();
		}

		SetInfo setInfo;
		try {
			setInfo = sil.getByCode(mtgaCard.getSet());
		} catch (NullPointerException e) {
			throw new MappingException("Unknown set: " + mtgaCard.getSet(), mtgaCard);
		}

		Optional<SetInfo> digitalSetInfo;
		try {
			fixDigitalRelease(mtgaCard);
			digitalSetInfo = sil.getByCodeNullFriendly(mtgaCard.getDigitalReleaseSet());
		} catch (NullPointerException e) {
			throw new MappingException("Unknown digitalSet: " + mtgaCard.getDigitalReleaseSet(), mtgaCard);
		}

		String name;
		try {
			name = rawCardDatabaseDAO.getEnglishNonFormattedLocalization(mtgaCard.getTitleId());
		} catch (IllegalStateException e) {
			throw new MappingException("Card name could not be found", mtgaCard);
		}

		// collectorMax is null if it isn't found in pack
		// Historic Anthologies have DigitalReleaseSet set to "AHAX" where X is the number.
		// otherwise it is empty
		boolean inBooster = checkInBooster(mtgaCard);

		CardInfo ci = new CardInfo(mtgaCard.getGrpId(), name, Rarity.valueByMtgaCode(mtgaCard.getRarity()),
				setInfo, inBooster, digitalSetInfo, Boolean.TRUE.equals(mtgaCard.getIsRebalanced()));

		return Optional.of(ci);
	}

	private void fixDigitalRelease(final MtgaCard mtgaCard) {
		if (mtgaCard.getGrpId() == 48499 || mtgaCard.getGrpId() == 49077) {
			// 48499, Hanna, Ship's Navigator and 49077, Talrand, Sky Summoner
			// both don't have a DigitalReleaseSet, should be the same as the other Brawl Commanders
			mtgaCard.setDigitalReleaseSet("BC20");
		} else if (mtgaCard.getGrpId() == 72243) {
			// seems MTGA got their Talrands mixed up, the one from JMP shouldn't have one
			mtgaCard.setDigitalReleaseSet(null);
		}
	}

	private boolean checkInBooster(final MtgaCard mtgaCard) {
		if ("ANB".equals(mtgaCard.getSet())) {
			// ANB has a collectorMax set although there is no ANB pack
			// (?booster finds them in Arena)
			return false;
		}
		if ("JMP".equals(mtgaCard.getSet()) || "J21".equals(mtgaCard.getSet())) {
			// the JMP-only cards (Emiel, Muxus, etc.) and the Arena replacements (Archon, Gadwick) have a CollectorMax
			// in J21 the new digital-only cards (Davriel, Teyo, etc.) have it set
			// other cards in those set don't have it set, so would result in false
			// that difference isn't interesting for statistics, instead consider them all as inBooster instead
			return true;
		}
		if (!COLLECTOR_NUMBER_PATTERN.matcher(mtgaCard.getCollectorNumber()).matches()) {
			// those with a "special" Collector Number like "GR8" from
			// Vraska, Golgari Queen (the Beta reward) are not in booster
			return false;
		}
		if (SPECIAL_ART_CARDS.contains(mtgaCard.getGrpId())) {
			// special art cards back when card styles weren't a thing yet
			return false;
		}
		if (mtgaCard.getCollectorMax() == null
				|| Integer.parseInt(mtgaCard.getCollectorMax()) < Integer.parseInt(mtgaCard.getCollectorNumber())) {
			// cards with no CollectorMax (Demon of Loathing, THB)
			// cards with a CollectorMax lower than its CollectorNumber (Nexus of Fate, M19)
			return false;
		}
		// Anthology cards are not in booster
		return mtgaCard.getDigitalReleaseSet() == null || mtgaCard.getDigitalReleaseSet().startsWith("Y22");
	}
}
