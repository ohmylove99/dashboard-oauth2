package org.octopus.dashboard.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

//import freemarker.template.TemplateException;

public class FreemarkerConfiguration {
	/*@Bean
	public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
		FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
		factory.setTemplateLoaderPaths("classpath:templates",
				"src/main/resource/templates");
		factory.setDefaultEncoding("UTF-8");
		FreeMarkerConfigurer result = new FreeMarkerConfigurer();
		result.setConfiguration(factory.createConfiguration());
		return result;
	}*/
}
