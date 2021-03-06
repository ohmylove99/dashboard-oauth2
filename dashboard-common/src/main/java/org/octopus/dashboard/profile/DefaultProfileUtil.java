package org.octopus.dashboard.profile;

import org.octopus.dashboard.config.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class to load a Spring profile to be used as default when there is no
 * <code>spring.profiles.active</code> set in the environment or as command line argument.
 * If the value is not available in <code>application.yml</code> then <code>dev</code>
 * profile will be used as default.
 */
public final class DefaultProfileUtil {

	private static final Logger log = LoggerFactory.getLogger(DefaultProfileUtil.class);

	private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

	private static final Properties BUILD_PROPERTIES = readProperties();

	/**
	 * Get a default profile from <code>application.yml</code>.
	 */
	public static String getDefaultActiveProfiles() {
		if (BUILD_PROPERTIES != null) {
			String activeProfile = BUILD_PROPERTIES.getProperty(SPRING_PROFILE_ACTIVE);
			if (activeProfile != null && !activeProfile.isEmpty()
					&& (activeProfile.contains(ConfigConstants.SPRING_PROFILE_DEVELOPMENT)
							|| activeProfile.contains(
									ConfigConstants.SPRING_PROFILE_PRODUCTION))) {
				return activeProfile;
			}
		}
		log.warn("No Spring profile configured, running with default profile: {}",
				ConfigConstants.SPRING_PROFILE_DEVELOPMENT);
		return ConfigConstants.SPRING_PROFILE_DEVELOPMENT;
	}

	/**
	 * Set a default to use when no profile is configured.
	 */
	public static void addDefaultProfile(SpringApplication app) {
		Map<String, Object> defProperties = new HashMap<>();

		defProperties.put(SPRING_PROFILE_ACTIVE, getDefaultActiveProfiles());
		app.setDefaultProperties(defProperties);
	}

	private static Properties readProperties() {
		try {
			YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
			factory.setResources(new ClassPathResource("config/application.yml"));
			return factory.getObject();
		}
		catch (Exception e) {
			log.error("Failed to read application.yml to get default profile");
		}
		return null;
	}
}
