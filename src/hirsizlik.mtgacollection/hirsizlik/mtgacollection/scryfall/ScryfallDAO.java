package hirsizlik.mtgacollection.scryfall;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import hirsizlik.mtgacollection.jackson.scryfall.set.ScryfallSet;
import hirsizlik.mtgacollection.mapper.MapScryfallSet;

/**
 * Access to Scryfall using their API (over HTTPS)
 */
public class ScryfallDAO {

	private final HttpClient client;
	private static final String URL_GET_SET_BY_CODE = "https://api.scryfall.com/sets/";//+code
	private static final Duration MIN_DURATION_BETWEEN_CALLS = Duration.ofMillis(200);
	private Instant lastAccess = Instant.EPOCH;

	public ScryfallDAO() {
		this.client = HttpClient.newHttpClient();
	}

	/**
	 * Loads the Set from Scryfall
	 * @param setCode the SetCode
	 * @return the Set from Scryfall (optional, empty if not found)
	 * @throws IOException Error from HttpClient (except 404, then empty)
	 * @throws InterruptedException Error from HttpClient (Timeout, etc.)
	 */
	public Optional<ScryfallSetInfo> getSet(final String setCode) throws IOException, InterruptedException {
		Optional<String> resp = call(URL_GET_SET_BY_CODE + setCode);
		if(resp.isEmpty())
			return Optional.empty();

		ObjectMapper omapper = new ObjectMapper();
		ScryfallSet ss = omapper.readValue(resp.get(), ScryfallSet.class);

		return Optional.of(new MapScryfallSet().applyDirect(ss));
	}

	private Optional<String> call(final String uri) throws InterruptedException, IOException {
		sleepIfToFastAndSetLastAccess();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		return switch (response.statusCode()) {
			case 200 -> Optional.of(response.body());
			case 404 -> Optional.empty();
			default -> throw new IOException("Unexpected status code " + response.statusCode());
		};
	}

	/**
	 * Sleeps if the duration since last access is less than MIN_DURATION_BETWEEN_CALLS.
	 * @throws InterruptedException thrown by sleep, should never happen
	 */
	private void sleepIfToFastAndSetLastAccess() throws InterruptedException {
		Instant now = Instant.now();
		Duration sinceLastAccess = Duration.between(lastAccess, now);
		if(sinceLastAccess.compareTo(MIN_DURATION_BETWEEN_CALLS) < 0) {
			Thread.sleep(MIN_DURATION_BETWEEN_CALLS.minus(sinceLastAccess).toMillis());
		}

		lastAccess = now;
	}
}
