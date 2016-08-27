package org.octopus.dashboard.api.doc;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.octopus.dashboard.config.ConfigConstants;

@Configuration
@EnableSwagger2
@Profile("!" + ConfigConstants.SPRING_PROFILE_PRODUCTION)
public class SwaggerConfig implements EnvironmentAware {

	private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	private RelaxedPropertyResolver propertyResolver;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
	}

	@Bean
	public Docket swaggerSpringfoxDocket() {
		if (log.isDebugEnabled())
			log.debug("Starting Swagger");
		StopWatch watch = new StopWatch();
		watch.start();
		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.genericModelSubstitutes(ResponseEntity.class).forCodeGeneration(true)
				.genericModelSubstitutes(ResponseEntity.class)
				.directModelSubstitute(java.time.LocalDate.class, String.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class).select()
				.paths(regex(DEFAULT_INCLUDE_PATTERN)).build();
		watch.stop();
		log.info("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return docket;
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(propertyResolver.getProperty("title"),
				propertyResolver.getProperty("description"),
				propertyResolver.getProperty("version"),
				propertyResolver.getProperty("termsOfServiceUrl"),
				new Contact(propertyResolver.getProperty("contact"), "", ""),
				propertyResolver.getProperty("license"),
				propertyResolver.getProperty("licenseUrl"));
	}
}