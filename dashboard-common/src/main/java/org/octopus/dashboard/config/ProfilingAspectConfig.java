package org.octopus.dashboard.config;

import org.octopus.dashboard.aop.ProfilingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableAspectJAutoProxy
public class ProfilingAspectConfig {

	@Bean
	@Profile(ConfigConstants.SPRING_PROFILE_DEVELOPMENT)
	public ProfilingAspect profilingAspect() {
		return new ProfilingAspect();
	}
}
