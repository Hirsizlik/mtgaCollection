package hirsizlik.mtgacollection.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import hirsizlik.mtgacollection.bo.inventory.Inventory;
import hirsizlik.mtgacollection.jackson.inventory.PlayerInventory;
import hirsizlik.mtgacollection.jackson.playercards.PlayerCards;
import hirsizlik.mtgacollection.mapper.MapMtgaInventoryToInventory;

/**
 * Class to read the inventory and collected cards from the log file.
 * Actual file reading happens in {@link LogfileBlockReader}. Here the read json blocks are mapped to
 * their classes.
 * @author Markus Schagerl
 *
 */
public class LogfileParser {

	private final Inventory inventory;
	private final Map<Integer, Integer> cardMap;

	private static final String GET_PLAYER_INVENTORY = "<== PlayerInventory.GetPlayerInventory";
	private static final String GET_PLAYER_CARDS = "<== PlayerInventory.GetPlayerCardsV3";

	private LogfileParser(final Path pathToLogfile) throws IOException {
		Map<String, String> jsonBlocks = readJsonBlocks(pathToLogfile);

		ObjectMapper omapper = new ObjectMapper();
		PlayerInventory pi = omapper.readValue(jsonBlocks.get(GET_PLAYER_INVENTORY), PlayerInventory.class);
		// Mapping should always be successful
		inventory = new MapMtgaInventoryToInventory().apply(pi).get();
		cardMap = omapper.readValue(jsonBlocks.get(GET_PLAYER_CARDS), PlayerCards.class).getPayload();
	}

	/**
	 * Starts the parsing, returning the parsed inventory and collected cards.
	 * @param pathToLogfile the path to the logfile
	 * @param setInfoLoader to load SetInformation, used for the inventory
	 * @return a instance of this class with the inventory and a map of all collected cards and the amount of them.
	 * @throws IOException
	 */
	public static LogfileParser parse(final Path pathToLogfile) throws IOException {
		return new LogfileParser(pathToLogfile);
	}

	private static Map<String, String> readJsonBlocks(final Path pathToLogfile) throws IOException {
		try(LogfileBlockReader r = new LogfileBlockReader(pathToLogfile)){
			return r.getLastBlockAfter(GET_PLAYER_INVENTORY, GET_PLAYER_CARDS);
		}
	}

	/**
	 * @return the parsed {@link Inventory}
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @return the map with cardId as key and the amount of cards (1-4) as value.
	 */
	public Map<Integer, Integer> getCardIdAmountMap(){
		return cardMap;
	}

}
