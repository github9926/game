package fi.esamatti.game.db;

import org.springframework.data.repository.CrudRepository;

public interface WalletEventRepository extends CrudRepository<WalletEvent, Long> {
	WalletEvent findById(long id);
}
