package org.octopus.dashboard.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
// @ComponentScan({ "org.octopus.dashboard.api" })
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");

		/*
		 * ResourceHandlerRegistration resourceRegistration = registry
		 * .addResourceHandler("resources/**");
		 */
		// resourceRegistration.addResourceLocations("/resources/**");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/**");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/**");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/**");
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:/resources/");

	}
}
