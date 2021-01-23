package fi.esamatti.game;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Path("/accounts")
@Service
public class AccountResource {
	@Autowired
	private PlayerRepository playerRepository;
	
    @GET
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String getPlayerCount() {
    	final long playerCount = playerRepository.count();
    	System.out.println("Players: " + playerCount);
    	
		return "Player count: " + playerCount;
    }
}
