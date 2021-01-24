package fi.esamatti.game.db;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import fi.esamatti.game.rest.InputJson;
import fi.esamatti.game.rest.OutputJson;

public class DbApi {

	private final PlayerRepository playerRepository;

	public DbApi(final PlayerRepository repository) {
		playerRepository = repository;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public OutputJson deposit(final InputJson event) {
		final Player player = playerRepository.findById(event.getPlayerId());
		final long newBalance = player.getBalance() + event.getAmount();

		player.setBalance(newBalance);
		playerRepository.save(player);

		final OutputJson outputJson = new OutputJson();
		outputJson.setBalance(newBalance);
		return outputJson;
	}

}
