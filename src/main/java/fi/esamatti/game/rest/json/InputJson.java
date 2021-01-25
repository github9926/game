package fi.esamatti.game.rest.json;

public class InputJson {

	private long eventId;
	private long playerId;
	private long amount;

	public long getEventId() {
		return eventId;
	}

	public void setEventId(final long eventId) {
		this.eventId = eventId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(final long playerId) {
		this.playerId = playerId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(final long amount) {
		this.amount = amount;
	}
}
