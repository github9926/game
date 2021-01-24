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
import fi.esamatti.game.rest.OutputJson;

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
	public void testDepositAndBuy() throws Exception {
		final long playerId = 1L;
		final long depositEventId = 1L;
		final long amount = 3L;

		final InputJson depositInput = new InputJson();
		depositInput.setEventId(depositEventId);
		depositInput.setPlayerId(playerId);
		depositInput.setAmount(amount);

		final HttpHeaders headers = new HttpHeaders();

		final HttpEntity<InputJson> request = new HttpEntity<>(depositInput, headers);

		// Deposit money
		final String urlString = "https://localhost:" + port + "/deposit/";
		final URI uri = new URI(urlString);
		final ResponseEntity<OutputJson> firstResponse = restTemplate.postForEntity(uri, request, OutputJson.class);
		assertThat(firstResponse.getStatusCodeValue()).isEqualTo(200);

		final long firstBalance = firstResponse.getBody().getBalance();
		assertThat(firstBalance).isEqualTo(amount);

		// Send same deposition event second time, and verify the balance doesn't change
		final ResponseEntity<OutputJson> secondResponse = restTemplate.postForEntity(uri, request, OutputJson.class);
		assertThat(secondResponse.getStatusCodeValue()).isEqualTo(200);

		final long secondBalance = secondResponse.getBody().getBalance();
		assertThat(secondBalance).isEqualTo(amount);

		// Buy a game
		final URI buyUri = new URI("https://localhost:" + port + "/buy/");
		final InputJson buyInput = new InputJson();
		buyInput.setEventId(depositEventId + 1L);
		buyInput.setPlayerId(playerId);
		buyInput.setAmount(amount);
		final HttpEntity<InputJson> buyRequest = new HttpEntity<>(buyInput, headers);
		final ResponseEntity<OutputJson> buyResponse = restTemplate.postForEntity(buyUri, buyRequest, OutputJson.class);
		assertThat(buyResponse.getStatusCodeValue()).isEqualTo(200);

		final long buyBalance = buyResponse.getBody().getBalance();
		assertThat(buyBalance).isEqualTo(0L);
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
