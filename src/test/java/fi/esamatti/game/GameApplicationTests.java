package fi.esamatti.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import fi.esamatti.game.db.Player;
import fi.esamatti.game.db.PlayerRepository;

//import org.springframework.boot.test.context.SpringBootTest;
//@SpringBootTest
@DataJpaTest
class GameApplicationTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PlayerRepository players;

	@Test
	public void testFindByLastName() {
		final Player player = new Player("first", "last");
		entityManager.persist(player);

		final List<Player> findByLastName = players.findByLastName(player.getLastName());

		assertThat(findByLastName).extracting(Player::getLastName).containsOnly(player.getLastName());
	}
}
