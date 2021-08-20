package hirsizlik.mtgacollection.mapper;

import java.util.Map;

import hirsizlik.mtgacollection.bo.CardInfo;
import hirsizlik.mtgacollection.bo.Rarity;
import hirsizlik.mtgacollection.bo.SetInfo;
import hirsizlik.mtgacollection.database.SetInfoLoader;
import hirsizlik.mtgacollection.jackson.mtga.card.MtgaCard;

/**
 * Maps a card from the MTGA json files to a card used in this program.
 *
 * @author Markus Schagerl
 */
public class MapMtgaCardToCardInfo implements Mapper<MtgaCard, CardInfo>{

	private final Map<Integer, String> locMap;
	private final SetInfoLoader sil;

	/**
	 * Creates the Mapper.
	 *
	 * @param locMap the english localisation map, used to map name ids to their english name.
	 * @param setInfoLoader to load the set for a card. Unknown sets produce a error.
	 */
	public MapMtgaCardToCardInfo(final Map<Integer, String> locMap, final SetInfoLoader setInfoLoader) {
		this.locMap = locMap;
		this.sil = setInfoLoader;
	}

	@Override
	public MappingResult<MtgaCard, CardInfo> apply(final MtgaCard mtgaCard) {
		if(Boolean.TRUE.equals(mtgaCard.getIsToken())) {
			return MappingResult.createError(mtgaCard, "Card is a token", false);
		}

		if(Boolean.FALSE.equals(mtgaCard.getIsPrimaryCard())) {
			return MappingResult.createError(mtgaCard, "Card is not primary (e.g. half of a split card)", false);
		}

		SetInfo setInfo = sil.getByCode(mtgaCard.getSet());
		if(setInfo == sil.getUnknownSet()) {
			return MappingResult.createError(mtgaCard, "Unknown set: " + mtgaCard.getSet());
		}

		String name = locMap.get(mtgaCard.getTitleId());
		if(name == null) {
			return MappingResult.createError(mtgaCard, "Card name could not be found");
		}

		// collectorMax is empty if it isn't found in pack
		// Historic Anthologies have DigitalReleaseSet set to "AHAX" where X is the number.
		// otherwise it is empty
		boolean inBooster = checkInBooster(mtgaCard);

		CardInfo ci = new CardInfo(mtgaCard.getGrpid(), name, Rarity.valueByMtgaCode(mtgaCard.getRarity()), setInfo, inBooster);

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

		return mtgaCard.getCollectorMax().length() != 0 && mtgaCard.getDigitalReleaseSet().length() == 0;
	}
}
