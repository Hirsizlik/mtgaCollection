package hirsizlik.mtgacollection.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import hirsizlik.mtgacollection.jackson.mtga.card.MtgaCard;
import hirsizlik.mtgacollection.jackson.mtga.localisation.Localisation;

/**
 * Parses the MTGA card and localization files.
 *
 * @author Markus Schagerl
 */
public final class MtgaCardLocParser {

	private MtgaCardLocParser() {}

	/**
	 * Holds the parsed values.
	 *
	 * @author Markus Schagerl
	 */
	public static record MtgaCardLoc(
			/** the parsed list of cards */
			List<MtgaCard> cardList,
			/** the parsed localization, containing names (and other strings) of all cards in American English */
			Localisation englishLocalization) {}

	/**
	 * Parses the files with the card and localization data.
	 *
	 * @param toCard path to the file containing card information
	 * @param toLoc path to the file containing localization information
	 * @return the parsed results
	 * @throws IOException error while reading
	 */
	public static MtgaCardLoc parse(final Path toCard, final Path toLoc) throws IOException {
		ObjectMapper om = new ObjectMapper();
		List<MtgaCard> cardList = om.readerForListOf(MtgaCard.class).readValue(toCard.toFile());
		List<Localisation> locList = om.readerForListOf(Localisation.class).readValue(toLoc.toFile());
		Localisation english = locList.stream().filter(l -> l.getIsoCode().equals("en-US")).findAny()
				.orElseThrow(() -> new IllegalStateException("English localization couldn't be found!"));

		return new MtgaCardLoc(cardList, english);
	}

}
