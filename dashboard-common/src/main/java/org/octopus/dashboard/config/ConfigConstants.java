package org.octopus.dashboard.config;

import org.octopus.dashboard.shared.security.AuthoritiesConstants;

public class ConfigConstants extends AuthoritiesConstants {

	public static final String SPRING_PROFILE_LOCAL = "local";
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
	public static final String SPRING_PROFILE_PRODUCTION = "prod";
	public static final String SPRING_PROFILE_FAST = "fast";

	public static final String SYSTEM_ACCOUNT = "system";

	protected ConfigConstants() {
	}
}
