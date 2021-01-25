package fi.esamatti.game.db;

import org.springframework.data.repository.CrudRepository;

import fi.esamatti.game.db.entity.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
	Player findById(long id);
}
