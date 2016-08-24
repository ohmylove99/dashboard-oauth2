package org.octopus.dashboard.config.web;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.octopus.dashboard.config.AppProperties;
import org.octopus.dashboard.config.ConfigConstants;
import org.octopus.dashboard.shared.web.filter.CachingHttpHeadersFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfigurer
		implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

	private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

	@Autowired
	private Environment env;
	@Autowired
	private AppProperties appProperties;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		if (env.getActiveProfiles().length != 0) {
			log.info("Web application configuration, using profiles: {}",
					Arrays.toString(env.getActiveProfiles()));
		}
		EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST,
				DispatcherType.FORWARD, DispatcherType.ASYNC);
		if (env.acceptsProfiles(ConfigConstants.SPRING_PROFILE_PRODUCTION)) {
			initCachingHttpHeadersFilter(servletContext, disps);
		}
		log.info("Web application fully configured");
	}

	/**
	 * Set up Mime types and, if needed, set the document root.
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		mappings.add("html", "text/html;charset=utf-8");
		mappings.add("json", "text/html;charset=utf-8");
		container.setMimeMappings(mappings);
		setLocationForStaticAssets(container);
	}

	private void setLocationForStaticAssets(
			ConfigurableEmbeddedServletContainer container) {
		File root;
		String prefixPath = resolvePathPrefix();
		if (env.acceptsProfiles(ConfigConstants.SPRING_PROFILE_PRODUCTION)) {
			root = new File(prefixPath + "build/www/");
		}
		else {
			root = new File(prefixPath + "src/main/webapp/");
		}
		if (root.exists() && root.isDirectory()) {
			container.setDocumentRoot(root);
		}
	}

	/**
	 * Resolve path prefix to static resources.
	 */
	private String resolvePathPrefix() {
		String fullExecutablePath = this.getClass().getResource("").getPath();
		String rootPath = Paths.get(".").toUri().normalize().getPath();
		String extractedPath = fullExecutablePath.replace(rootPath, "");
		int extractionEndIndex = extractedPath.indexOf("build/");
		if (extractionEndIndex <= 0) {
			return "";
		}
		return extractedPath.substring(0, extractionEndIndex);
	}

	/**
	 * Initializes the caching HTTP Headers Filter.
	 */
	private void initCachingHttpHeadersFilter(ServletContext servletContext,
			EnumSet<DispatcherType> disps) {
		log.debug("Registering Caching HTTP Headers Filter");
		FilterRegistration.Dynamic cachingHttpHeadersFilter = servletContext
				.addFilter("cachingHttpHeadersFilter", new CachingHttpHeadersFilter());

		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/content/*");
		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*");
		cachingHttpHeadersFilter.setAsyncSupported(true);
	}

	@Bean
	@ConditionalOnProperty(name = "app.cors.allowed-origins")
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		CorsConfiguration appconfig = appProperties.getCors();
		source.registerCorsConfiguration("/api/**", appconfig);
		source.registerCorsConfiguration("/v2/api-docs", appconfig);
		source.registerCorsConfiguration("/oauth/**", appconfig);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
}
