package org.octopus.dashboard.config.web;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.octopus.dashboard.springmvc.controller.*")
public class WebConfig extends WebMvcConfigurerAdapter {
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:/static/resources/");
		registry.addResourceHandler("/css/**")
				.addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**")
				.addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/images/**")
				.addResourceLocations("classpath:/static/images/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");

		if (!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**")
					.addResourceLocations("classpath:/META-INF/resources/webjars/");
		}
		if (!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**")
					.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		}
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/signin").setViewName("signin");
		registry.addViewController("/dashboard").setViewName("dashboard");
		registry.addViewController("/index").setViewName("index");
        registry.addViewController("/results").setViewName("results");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/form").setViewName("form");
		registry.addViewController("/error/404.html").setViewName("404");
		registry.addViewController("/error/505.html").setViewName("505");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@SuppressWarnings("unused")
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED,
						"/401.html");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
						"/500.html");
				ErrorPage error505Page = new ErrorPage(
						HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "/505.html");
				ErrorPage error506Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED,
						"/405.html");
				container.addErrorPages(error404Page, error505Page);
			}
		};
	}
	
	/*@Configuration
	public static class WebMvcConfig extends WebMvcConfigurationSupport {
	    @Override
	    protected void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new ThymeleafLayoutInterceptor());
	    }
	}*/
}


