package fi.esamatti.game.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private Long balance;

	protected Player() {
	}

	public Player(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		balance = 0L;
	}

	@Override
	public String toString() {
		return String.format("Player(id:%d, firstName:'%s', lastName:'%s', balance:'%s']", id, firstName, lastName,
				balance);
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(final long balance) {
		this.balance = balance;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
}
