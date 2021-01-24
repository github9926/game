package fi.esamatti.game.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import fi.esamatti.game.db.DbApi;

@Path("/")
@Service
public class AccountResource {
	
	@Autowired
	private DbApi dbApi;
    
    @Path("/deposit")
    @Consumes({ MediaType.APPLICATION_JSON })
    @POST
    public String deposit(InputJson event) {
    	dbApi.deposit(event);
		return "json";    
    }
}


