package hirsizlik.mtgacollection.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import hirsizlik.mtgacollection.jackson.mtga.card.MtgaCard;

/**
 * Parses the MTGA card and localization files.
 *
 * @author Markus Schagerl
 */
public final class MtgaCardParser {

	private MtgaCardParser() {}

	/**
	 * Parses the files with the card data.
	 *
	 * @param toCard path to the file containing card information
	 * @return the parsed results
	 * @throws IOException error while reading
	 */
	public static List<MtgaCard> parse(final Path toCard) throws IOException {
		ObjectMapper om = new ObjectMapper();
		return om.readerForListOf(MtgaCard.class).readValue(toCard.toFile());
	}

}
