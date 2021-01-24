package fi.esamatti.game.db;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class WalletEvent {
	public enum WalletEventType {
		Deposit, Buy;
	}

	@Id
	private Long id;

	public void setId(final Long id) {
		this.id = id;
	}

	private Long playerId;
	@Enumerated(EnumType.STRING)
	private WalletEventType eventType;
	private Long amount;

	public Long getAmount() {
		return amount;
	}

	public void setAmount(final Long amount) {
		this.amount = amount;
	}

	protected WalletEvent() {
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(final Long playerId) {
		this.playerId = playerId;
	}

	public Long getId() {
		return id;
	}

	public WalletEventType getEventType() {
		return eventType;
	}

	public void setEventType(final WalletEventType eventType) {
		this.eventType = eventType;
	}
}
