package fi.esamatti.game.db;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import fi.esamatti.game.rest.InputJson;

public class DbApi {

	private final PlayerRepository playerRepository;
	
	public DbApi(PlayerRepository repository) {
		playerRepository = repository;
	}

    @Transactional(isolation = Isolation.REPEATABLE_READ)
	public void deposit(InputJson event) {
    	//Player player = playerRepository.findById(event.getPlayerId());
		// TODO Auto-generated method stub
		
	}

}
