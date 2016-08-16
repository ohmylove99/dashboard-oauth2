package org.octopus.dashboard.api.rest;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.octopus.dashboard.data.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingRestEndPoint {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(GreetingRestEndPoint.class);

	private static final String template = "Hello, %s!";

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = "/api/rest/greeting", method = RequestMethod.GET)
	public Map<Long, String> greeting(@AuthenticationPrincipal User user) {
		return Collections.singletonMap(counter.incrementAndGet(),
				String.format(template, user.getName()));
	}
}
