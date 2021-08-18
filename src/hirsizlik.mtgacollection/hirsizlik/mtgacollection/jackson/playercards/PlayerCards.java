package hirsizlik.mtgacollection.jackson.playercards;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerCards {
	private int id;
	private Map<Integer, Integer> payload;

	@JsonProperty("id")
	public int getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(final int id) {
		this.id = id;
	}

	@JsonProperty("payload")
	public Map<Integer, Integer> getPayload() {
		return payload;
	}

	@JsonProperty("payload")
	public void setPayload(final Map<Integer, Integer> payload) {
		this.payload = payload;
	}
}
