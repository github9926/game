package fi.esamatti.game.db;

import org.springframework.data.repository.CrudRepository;

import fi.esamatti.game.db.entity.WalletEvent;

public interface WalletEventRepository extends CrudRepository<WalletEvent, Long> {
	WalletEvent findById(long id);
}
