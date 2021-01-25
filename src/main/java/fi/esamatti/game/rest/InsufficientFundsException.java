package fi.esamatti.game.rest;

public class InsufficientFundsException extends RuntimeException {

	private static final long serialVersionUID = -7615222151693860303L;

	public InsufficientFundsException(final Long balance, final Long buyAmount) {
		super("Insufficient funds! Account only has " + balance + ", but tried to buy " + buyAmount);
	}
}
