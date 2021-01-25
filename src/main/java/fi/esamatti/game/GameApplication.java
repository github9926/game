package fi.esamatti.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.esamatti.game.db.DbApi;
import fi.esamatti.game.db.PlayerRepository;
import fi.esamatti.game.db.WalletEventRepository;
import fi.esamatti.game.db.entity.Player;

@SpringBootApplication
public class GameApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameApplication.class);

	public static void main(final String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(final PlayerRepository repository) {
		return (args) -> {
			// Generate some test data for this demo
			repository.save(new Player("First1", "Last1"));
			repository.save(new Player("First2", "Last2"));
			repository.save(new Player("First3", "Last3"));
			LOGGER.info("Saved three players to the database");
		};
	}

	@Bean
	public DbApi dbApi(final PlayerRepository repository, final WalletEventRepository eventRepository) {
		return new DbApi(repository, eventRepository);
	}
}
