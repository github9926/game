package fi.esamatti.game.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Actor Not Found")
public class InsufficientFundsException extends ResponseStatusException
		implements ExceptionMapper<InsufficientFundsException> {

	private static final long serialVersionUID = -7615222151693860303L;

	public InsufficientFundsException(final Long balance, final Long buyAmount) {
		super(HttpStatus.CONFLICT,
				"Insufficient funds! Account only has " + balance + ", but tried to buy " + buyAmount);
	}

	@Override
	public Response toResponse(final InsufficientFundsException exception) {
		return Response.status(404).entity(exception.getMessage()).type("text/plain").build();
	}
}
