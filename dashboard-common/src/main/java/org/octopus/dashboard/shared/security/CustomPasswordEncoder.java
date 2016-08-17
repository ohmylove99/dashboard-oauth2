package org.octopus.dashboard.shared.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.octopus.dashboard.shared.utils.PasswordUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
	private final Log logger = LogFactory.getLog(getClass());

	public String encode(CharSequence rawPassword) {
		return PasswordUtils.hashPassword(rawPassword.toString());
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword == null || encodedPassword.length() == 0) {
			logger.warn("Empty encoded password");
			return false;
		}
		if (rawPassword == null || rawPassword.length() == 0) {
			logger.warn("Empty raw Password");
			return false;
		}
		return encodedPassword.toString()
				.equals(PasswordUtils.hashPassword(rawPassword.toString()));
	}

	public CustomPasswordEncoder() {
	}

}
