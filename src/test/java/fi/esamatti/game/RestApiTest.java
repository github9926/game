package fi.esamatti.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import fi.esamatti.game.rest.InputJson;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test-application.properties")
class RestApiTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${test.truststore.location}")
	private Resource trustStore;

	@Value("${test.truststore.password}")
	private String trustStorePassword;

	@Test
	public void httpsRequired() throws Exception {
		assertThat(restTemplate.getForObject("http://localhost:" + port + "/deposit", String.class))
				.contains("Bad Request");
	}

	@Test
	public void testDeposit() throws Exception {
		final InputJson input = new InputJson();
		input.setEventId(0);
		input.setPlayerId(0);
		input.setAmount(3);

		final HttpHeaders headers = new HttpHeaders();

		final HttpEntity<InputJson> request = new HttpEntity<>(input, headers);

		final ResponseEntity<String> result = restTemplate
				.postForEntity(new URI("https://localhost:" + port + "/deposit/"), request, String.class);
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}

	RestTemplate restTemplate() throws Exception {
		final SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray()).build();
		final SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
		final HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(factory);
	}

}
