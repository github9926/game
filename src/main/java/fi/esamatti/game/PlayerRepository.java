package fi.esamatti.game;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

	List<Player> findByLastName(String lastName);

	Player findById(long id);
}
