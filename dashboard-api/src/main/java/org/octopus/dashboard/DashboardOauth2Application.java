package org.octopus.dashboard;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.octopus.dashboard.config.AppProperties;
import org.octopus.dashboard.config.ConfigConstants;
import org.octopus.dashboard.profile.DefaultProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties({ AppProperties.class })
public class DashboardOauth2Application extends SpringBootServletInitializer {
	/**
	 * Main method, used to run the application.
	 */
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(DashboardOauth2Application.class);
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(
				args);
		addDefaultProfile(app, source);
		Environment env = app.run(args).getEnvironment();
		log.info(
				"Access URLs:\n----------------------------------------------------------\n\t"
						+ "Local: \t\thttp://127.0.0.1:{}\n\t"
						+ "External: \thttp://{}:{}\n----------------------------------------------------------",
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		DefaultProfileUtil.addDefaultProfile(application.application());
		return application.sources(DashboardOauth2Application.class);
	}

	private static final Logger log = LoggerFactory
			.getLogger(DashboardOauth2Application.class);

	@Inject
	private Environment env;

	@PostConstruct
	public void initApplication() throws IOException {
		if (env.getActiveProfiles().length == 0) {
			log.warn("No Spring profile configured, running with default configuration");
		}
		else {
			log.info("Running with Spring profile(s) : {}",
					Arrays.toString(env.getActiveProfiles()));
			Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
			if (activeProfiles.contains(ConfigConstants.SPRING_PROFILE_DEVELOPMENT)
					&& activeProfiles
							.contains(ConfigConstants.SPRING_PROFILE_PRODUCTION)) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'dev' and 'prod' profiles at the same time.");
			}
			if (activeProfiles.contains(ConfigConstants.SPRING_PROFILE_PRODUCTION)
					&& activeProfiles.contains(ConfigConstants.SPRING_PROFILE_FAST)) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'prod' and 'fast' profiles at the same time.");
			}
		}
	}

	private static void addDefaultProfile(SpringApplication app,
			SimpleCommandLinePropertySource source) {
		if (!source.containsProperty("spring.profiles.active")
				&& !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
			app.setAdditionalProfiles(ConfigConstants.SPRING_PROFILE_DEVELOPMENT);
		}
	}
}
