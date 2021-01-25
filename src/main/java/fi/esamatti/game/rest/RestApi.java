package fi.esamatti.game.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.esamatti.game.db.DbApi;

@Path("/")
@Service
public class RestApi {

	@Autowired
	private DbApi dbApi;

	@Path("/deposit")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	public Response deposit(final InputJson event) {
		final OutputJson output = dbApi.deposit(event);
		return Response.status(Response.Status.OK).entity(output).build();
	}

	@Path("/buy")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	public Response buy(final InputJson event) {
		try {
			final OutputJson output = dbApi.buy(event);
			return Response.status(Response.Status.OK).entity(output).build();
		} catch (final InsufficientFundsException e) {
			return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
		}
	}
}
