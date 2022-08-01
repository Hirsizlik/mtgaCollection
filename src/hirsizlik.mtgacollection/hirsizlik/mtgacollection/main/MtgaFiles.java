package hirsizlik.mtgacollection.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to manage access to the MTGA files
 */
public class MtgaFiles {

	private static final String RAW_CARD_DATABASE = "Raw_CardDatabase";
	private static final String RAW_CARDS = "Raw_cards";
	private static record FileData(String nameKey, String hash, Path path) {}

	/**
	 * <code>Raw_\\w*_(.*)\\.mtga</code>
	 * <p>
	 * Should match for example <code>Raw_CardDatabase_b7147b55738bd011dab14df3c109fbcf.mtga</code>
	 */
	private static final Pattern NAME_HASH_PATTERN = Pattern.compile("Raw_\\w*_(.*)\\.mtga");

	private final Map<String, FileData> fileMap;

	public MtgaFiles(final Properties p) throws IOException {
		List<String> fileNames = List.of(RAW_CARDS, RAW_CARD_DATABASE);
		Path toMtgaData = Paths.get(p.getProperty("mtga.path"), "MTGA_Data/Downloads/Raw/");
		this.fileMap = getMtgaFilePaths(fileNames, toMtgaData);
		if (fileMap.size() != fileNames.size()) {
			throw new IllegalStateException(String.format("Not all necessary files were found (found: %s)", fileMap));
		}

	}

	private static Map<String, FileData> getMtgaFilePaths(final List<String> nameStart, final Path toMtgaData)
			throws IOException{
		try (Stream<Path> fileStream = Files.walk(toMtgaData)){
			return fileStream.map(p -> checkPath(p, nameStart))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toMap(FileData::nameKey, np -> np));
		}
	}

	private static Optional<FileData> checkPath(final Path p, final List<String> nameStart) {
		String fileName = p.getFileName().toString();
		return nameStart.stream()
				.filter(n -> fileName.startsWith(n) && fileName.endsWith(".mtga"))
				.map(n -> new FileData(n, getHash(fileName), p)).findFirst();
	}

	private static String getHash(final String filename) {
		Matcher m = NAME_HASH_PATTERN.matcher(filename);
		if (m.matches()) {
			return m.group(1);
		} else {
			throw new IllegalStateException("Filename %s did not match expected format!".formatted(filename));
		}
	}

	/**
	 * @return the path to the cardDatabase file (which is the localization file
	 *         with card names among other strings for all languages)
	 */
	public Path getCardDatabasePath() {
		return fileMap.get(RAW_CARD_DATABASE).path;
	}

	/**
	 * @return the path to the cards file (which contains all the card data. IDs of
	 *         names refer to the cardDatabase)
	 */
	public Path getCardsPath() {
		return fileMap.get(RAW_CARDS).path;
	}

	/**
	 * @return the Hash value of the CardDatabase file
	 */
	public String getCardDatabaseHash() {
		return fileMap.get(RAW_CARD_DATABASE).hash;
	}

	/**
	 * @return the Hash value of the cards file
	 */
	public String getCardsHash() {
		return fileMap.get(RAW_CARDS).hash;
	}
}
