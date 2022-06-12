package hirsizlik.mtgacollection.mapper;

import java.sql.SQLException;

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
public class MapMtgaCardToCardInfo implements Mapper<MtgaCard, CardInfo>{

	private final RawCardDatabaseDAO rawCardDatabaseDAO;
	private final SetInfoLoader sil;

	/**
	 * Creates the Mapper.
	 *
	 * @param rawCardDatabaseDAO access to the Localization database.
	 * @param setInfoLoader to load the set for a card. Unknown sets produce a error.
	 */
	public MapMtgaCardToCardInfo(final RawCardDatabaseDAO rawCardDatabaseDAO, final SetInfoLoader setInfoLoader) {
		this.rawCardDatabaseDAO = rawCardDatabaseDAO;
		this.sil = setInfoLoader;
	}

	@Override
	public MappingResult<MtgaCard, CardInfo> apply(final MtgaCard mtgaCard) {
		if(Boolean.TRUE.equals(mtgaCard.getIsToken())) {
			return MappingResult.createError(mtgaCard, "Card is a token", false);
		}

		if(Boolean.TRUE.equals(mtgaCard.getIsSecondaryCard())) {
			return MappingResult.createError(mtgaCard, "Card is secondary (e.g. half of a split card)", false);
		}

		SetInfo setInfo = sil.getByCode(mtgaCard.getSet());
		if(setInfo == sil.getUnknownSet()) {
			return MappingResult.createError(mtgaCard, "Unknown set: " + mtgaCard.getSet());
		}

		String name;
		try {
			name = rawCardDatabaseDAO.getEnglishNonFormattedLocalization(mtgaCard.getTitleId());
		} catch (SQLException e) {
			return MappingResult.createError(mtgaCard, "Card name could not be found");
		}

		// collectorMax is null if it isn't found in pack
		// Historic Anthologies have DigitalReleaseSet set to "AHAX" where X is the number.
		// otherwise it is empty
		boolean inBooster = checkInBooster(mtgaCard);

		CardInfo ci = new CardInfo(mtgaCard.getGrpid(), name, Rarity.valueByMtgaCode(mtgaCard.getRarity()),
				setInfo, inBooster);

		return MappingResult.createOk(mtgaCard, ci);
	}

	private boolean checkInBooster(final MtgaCard mtgaCard) {
		if(mtgaCard.getGrpid() == 48499) {
			// 48499 Hanna, Ship's Navigator should be inBooster = N,
			// but would be Y as it has no "DigitalReleaseSet", other similar Brawl cards have it set
			return false;
		}
		if("ANB".equals(mtgaCard.getSet())) {
			// ANB has a collectorMax set although there is no ANB pack
			// (?booster finds them in Arena)
			return false;
		}

		return mtgaCard.getCollectorMax() != null && mtgaCard.getDigitalReleaseSet() == null;
	}
}
