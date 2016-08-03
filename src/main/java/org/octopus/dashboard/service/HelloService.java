package org.octopus.dashboard.service;

import org.springframework.stereotype.Component;

@Component
public class HelloService {
	public String getHelloMessage() {
		return "Hello ";
	}

	public String getErrorMessage() throws Exception {
		throw new Exception("Hello Error");
	}
}
