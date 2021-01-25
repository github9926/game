package fi.esamatti.game.db;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import fi.esamatti.game.db.entity.Player;
import fi.esamatti.game.db.entity.WalletEvent;
import fi.esamatti.game.rest.InputJson;
import fi.esamatti.game.rest.InsufficientFundsException;
import fi.esamatti.game.rest.OutputJson;

public class DbApi {

	private final PlayerRepository playerRepository;
	private final WalletEventRepository eventRepository;

	public DbApi(final PlayerRepository repository, final WalletEventRepository eventRepository) {
		playerRepository = repository;
		this.eventRepository = eventRepository;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public OutputJson deposit(final InputJson inputJson) {
		final WalletEvent existingEvent = eventRepository.findById(inputJson.getEventId());
		final Player player = playerRepository.findById(inputJson.getPlayerId());
		final long oldBalance = player.getBalance();

		final OutputJson outputJson = new OutputJson();
		if (existingEvent == null) {
			final long newBalance = oldBalance + inputJson.getAmount();
			player.setBalance(newBalance);
			playerRepository.save(player);
			outputJson.setBalance(newBalance);

			final WalletEvent walletEvent = new WalletEvent();
			walletEvent.setId(inputJson.getEventId());
			walletEvent.setEventType(WalletEvent.WalletEventType.Deposit);
			walletEvent.setPlayerId(inputJson.getPlayerId());
			walletEvent.setAmount(inputJson.getAmount());
			eventRepository.save(walletEvent);
		} else {
			outputJson.setBalance(oldBalance);
		}

		return outputJson;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public OutputJson buy(final InputJson inputJson) {
		final WalletEvent existingEvent = eventRepository.findById(inputJson.getEventId());
		final Player player = playerRepository.findById(inputJson.getPlayerId());
		final long oldBalance = player.getBalance();

		final OutputJson outputJson = new OutputJson();
		if (existingEvent == null) {
			final long newBalance = oldBalance - inputJson.getAmount();
			if (newBalance < 0L) {
				throw new InsufficientFundsException(oldBalance, inputJson.getAmount());
			}
			player.setBalance(newBalance);
			playerRepository.save(player);
			outputJson.setBalance(newBalance);

			final WalletEvent walletEvent = new WalletEvent();
			walletEvent.setId(inputJson.getEventId());
			walletEvent.setEventType(WalletEvent.WalletEventType.Buy);
			walletEvent.setPlayerId(inputJson.getPlayerId());
			walletEvent.setAmount(inputJson.getAmount());
			eventRepository.save(walletEvent);
		} else {
			outputJson.setBalance(oldBalance);
		}

		return outputJson;
	}

}
