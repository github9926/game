package fi.esamatti.game.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfig extends ResourceConfig {
	public RestConfig() {
		register(RestApi.class);
	}
}