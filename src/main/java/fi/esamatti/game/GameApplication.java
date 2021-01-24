package fi.esamatti.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.esamatti.game.db.DbApi;
import fi.esamatti.game.db.WalletEventRepository;
import fi.esamatti.game.db.entity.Player;
import fi.esamatti.game.db.PlayerRepository;

@SpringBootApplication
public class GameApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameApplication.class);

	public static void main(final String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(final PlayerRepository repository) {
		return (args) -> {
			final String lastName = "Last1";
			repository.save(new Player("First1", lastName));
			repository.save(new Player("First2", "Last2"));
			repository.save(new Player("First3", lastName));

			LOGGER.info("Customers:");
			for (final Player customer : repository.findAll()) {
				LOGGER.info(customer.toString());
			}
			LOGGER.info("");

			final Player customer = repository.findById(1L);
			LOGGER.info("Customer with ID 1L");
			LOGGER.info(customer.toString());
			LOGGER.info("");

			LOGGER.info("Customer with findByLastName " + lastName);
			repository.findByLastName(lastName).forEach(player -> {
				LOGGER.info(player.toString());
			});
		};
	}

	@Bean
	public DbApi dbApi(final PlayerRepository repository, final WalletEventRepository eventRepository) {
		return new DbApi(repository, eventRepository);
	}
}
