package fi.esamatti.game.db;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import fi.esamatti.game.db.entity.Player;
import fi.esamatti.game.db.entity.WalletEvent;
import fi.esamatti.game.db.entity.WalletEventType;
import fi.esamatti.game.rest.InsufficientFundsException;
import fi.esamatti.game.rest.json.InputJson;
import fi.esamatti.game.rest.json.OutputJson;

public class DbApi {

	private final PlayerRepository playerRepository;
	private final WalletEventRepository eventRepository;

	public DbApi(final PlayerRepository repository, final WalletEventRepository eventRepository) {
		playerRepository = repository;
		this.eventRepository = eventRepository;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public OutputJson deposit(final InputJson inputJson) {
		return updateAccount(inputJson, WalletEventType.Deposit);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public OutputJson buy(final InputJson inputJson) {
		return updateAccount(inputJson, WalletEventType.Buy);
	}

	private OutputJson updateAccount(final InputJson inputJson, final WalletEventType eventType) {
		final WalletEvent existingEvent = eventRepository.findById(inputJson.getEventId());
		final Player player = playerRepository.findById(inputJson.getPlayerId());
		final long oldBalance = player.getBalance();

		final OutputJson outputJson = new OutputJson();
		if (existingEvent == null) {
			final long amount = inputJson.getAmount();
			final long changeAmount = eventType == WalletEventType.Buy ? -amount : amount;
			final long newBalance = oldBalance + changeAmount;
			if (newBalance < 0L) {
				throw new InsufficientFundsException(oldBalance, amount);
			}
			player.setBalance(newBalance);
			playerRepository.save(player);
			outputJson.setBalance(newBalance);

			final WalletEvent walletEvent = new WalletEvent();
			walletEvent.setId(inputJson.getEventId());
			walletEvent.setEventType(WalletEventType.Buy);
			walletEvent.setPlayerId(inputJson.getPlayerId());
			walletEvent.setAmount(amount);
			eventRepository.save(walletEvent);
		} else {
			outputJson.setBalance(oldBalance);
		}

		return outputJson;
	}

}
