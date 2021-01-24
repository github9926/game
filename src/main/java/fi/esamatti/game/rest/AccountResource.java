package fi.esamatti.game.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Path("/accounts")
@Service
public class AccountResource {
    
    @Path("/deposit")
    @Consumes({ MediaType.APPLICATION_JSON })
    @POST
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String deposit(InputJson json) {
		return "json";    
    }
}


