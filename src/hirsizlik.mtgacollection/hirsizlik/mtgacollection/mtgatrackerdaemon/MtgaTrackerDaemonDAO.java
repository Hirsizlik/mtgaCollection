package hirsizlik.mtgacollection.mtgatrackerdaemon;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Access to a MTGA Tracker Daemon Server for the card list.
 *
 * @author Markus Schagerl
 */
public class MtgaTrackerDaemonDAO {

	private static final Logger logger = LogManager.getLogger();

	private final HttpClient client;
	private final String serverUrl;

	private static final String GET_STATUS = "/status";
	private static final String GET_CARDS = "/cards";

	private record CardIdOwned(int grpId, int owned) {}

	/**
	 * Creates a instance and opens a http client
	 * @param serverUrl the server URL (e.g. "http://localhost:6842")
	 */
	public MtgaTrackerDaemonDAO(final String serverUrl) {
		this.serverUrl = serverUrl;
		if (!Objects.equals(System.getProperty("java.net.preferIPv6Addresses"), "true")) {
			logger.warn("java.net.preferIPv6Addresses is not true, "
					+ "mtga-tracker-daemon may not work on systems with IPv6 if not set");
		}
		this.client = HttpClient.newHttpClient();
	}

	/**
	 * @return true if MTGA is currently running
	 * @throws IOException
	 */
	public boolean isMtgaRunning() {
		String res = call(GET_STATUS);
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jn = mapper.readTree(res);
			return jn.get("isRunning").asBoolean();
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Error while reading the response", e);
		}
	}

	/**
	 * @return the cards in the collection, with key = card ID and value = Amount owned
	 */
	public Map<Integer, Integer> getCards() {
		String res = call(GET_CARDS);
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode cards = mapper.readTree(res).get("cards");
			var sp = Spliterators.spliterator(cards.iterator(), cards.size(),
					Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.IMMUTABLE);
			return StreamSupport.stream(sp, false)
				.map(this::createFromJsonNode)
				.collect(Collectors.toMap(CardIdOwned::grpId, CardIdOwned::owned));
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Error while reading the response", e);
		}
	}

	private CardIdOwned createFromJsonNode(final JsonNode jn) {
		int grpId = jn.get("grpId").asInt();
		int owned = jn.get("owned").asInt();
		return new CardIdOwned(grpId, owned);
	}

	private String call(final String action) {
		URI uri = URI.create(serverUrl + action);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.build();
		HttpResponse<String> response;
		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Could not send request to mtga-tracker-daemon at %s".formatted(uri), e);
		}

		return switch (response.statusCode()) {
			case 200 -> response.body();
			default -> throw new IllegalStateException("Unexpected status code " + response.statusCode());
		};
	}
}
