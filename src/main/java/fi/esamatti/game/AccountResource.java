package fi.esamatti.game;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.stereotype.Service;

@Path("/accounts")
@Service
public class AccountResource {
    @GET
    public String getEmployee() {
    	System.out.println("Printing");
        return "someResult";
    }
}
