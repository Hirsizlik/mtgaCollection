package hirsizlik.mtgacollection.mapper;

import java.util.Optional;
import java.util.function.Function;

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

		return mtgaCard.getCollectorMax() != null && (mtgaCard.getDigitalReleaseSet() == null ||
				mtgaCard.getDigitalReleaseSet().startsWith("Y22"));
	}
}
