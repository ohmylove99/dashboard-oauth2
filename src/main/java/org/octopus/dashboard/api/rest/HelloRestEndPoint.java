package org.octopus.dashboard.api.rest;

import java.util.Collections;
import java.util.Map;

import org.octopus.dashboard.shared.constants.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestEndPoint {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(HelloRestEndPoint.class);

	@RequestMapping(value = "/api/rest/hello", produces = MediaTypes.JSON_UTF_8)
	public Map<String, String> login() {
		return Collections.singletonMap("hello", "Jason");
	}
}
