package org.octopus.dashboard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class DashboardOauth2ApplicationTests {
	@LocalServerPort
	private int port;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testHello() throws Exception {

		String str = new TestRestTemplate().getForObject(
				"http://localhost:" + this.port + "/api/rest/hello", String.class);
		ResponseEntity<String> ent = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port + "/api/rest/hello", String.class);
		assertThat(str).isEqualTo("{\"hello\":\"Jason\"}");
		assertThat(ent.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(ent.getBody()).isEqualTo("{\"hello\":\"Jason\"}");
		;

		str = new TestRestTemplate().getForObject(
				"http://localhost:" + this.port + "/api/rest/hello/string", String.class);
		ent = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port + "/api/rest/hello/string", String.class);
		assertThat(str).isEqualTo("{\"hello\": \"Jason\"}");
		assertThat(ent.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(ent.getBody()).isEqualTo("{\"hello\": \"Jason\"}");
		str = new TestRestTemplate().getForObject(
				"http://localhost:" + this.port + "/api/rest/hello/resstring",
				String.class);
		ent = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port + "/api/rest/hello/resstring",
				String.class);
		assertThat(str).isEqualTo("{\"hello\": \"Jason\"}");
		assertThat(ent.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(ent.getBody()).isEqualTo("{\"hello\": \"Jason\"}");
		Map s = new TestRestTemplate().getForObject(
				"http://localhost:" + this.port + "/api/rest/hello/resmap", Map.class);
		ResponseEntity<Map> t = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port + "/api/rest/hello/string", Map.class);

		assertThat(s).containsValue("Jason");
		assertThat(t.getBody().get("hello")).isEqualTo("Jason");

		Map<String, String> params = new HashMap<String, String>();
		params.put("cnt", "1");
		String str2 = new TestRestTemplate().getForObject(
				"http://localhost:" + this.port + "/api/rest/error/{cnt}", String.class,
				params);
		// Error Format
		// {"timestamp":1470438226757,"status":500,"error":"Internal Server
		// Error","exception":"java.lang.Exception","message":"Test exception with
		// cnt=1","path":"/api/rest/error/1"}
		assertThat(str2).contains("500");
	}

	@Test
	public void contextLoads() {
	}

}
