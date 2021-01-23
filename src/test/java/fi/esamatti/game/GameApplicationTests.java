package fi.esamatti.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
		Player player = new Player("first", "last");
		entityManager.persist(player);

		List<Player> findByLastName = players.findByLastName(player.getLastName());

		assertThat(findByLastName).extracting(Player::getLastName).containsOnly(player.getLastName());
	}
}
