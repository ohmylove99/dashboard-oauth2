package org.octopus.dashboard.api.rest;

import java.util.Collections;
import java.util.Map;

import org.octopus.dashboard.service.HelloService;
import org.octopus.dashboard.shared.constants.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestEndPoint {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(HelloRestEndPoint.class);
	@Autowired
	private HelloService helloService;

	@RequestMapping(value = "/api/rest/hello", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, String> get() {
		return Collections.singletonMap("hello", "Jason");
	}

	@RequestMapping(value = "/api/rest/hello/string", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String getString() {
		return "{\"hello\": \"Jason\"}";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/api/rest/hello/resmap", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<Map> getStringResEntMap() {
		return new ResponseEntity<Map>(Collections.singletonMap("hello", "Jason"),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/api/rest/hello/resstring", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<String> getStringResEntString() {
		return new ResponseEntity<String>("{\"hello\": \"Jason\"}", HttpStatus.OK);
	}

	@RequestMapping(value = "/api/rest/error/{cnt}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, String> get(@PathVariable("cnt") int cnt) throws Exception {
		throw new Exception("Test exception with cnt=" + cnt);
	}

	@RequestMapping(value = "/api/rest/hello/service", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String getService() {
		return helloService.getHelloMessage();
	}

	@RequestMapping(value = "/api/rest/error/service/{cnt}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String getService(@PathVariable("cnt") int cnt) throws Exception {
		return helloService.getErrorMessage();
	}
}
