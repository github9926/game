package fi.esamatti.game;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

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
    
    @Path("/deposit")
    @Consumes({ MediaType.APPLICATION_JSON })
    @POST
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String deposit(InputJson json) {
		return "json";    
    }
}


