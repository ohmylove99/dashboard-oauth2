package org.octopus.dashboard.config.ds;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class DataSourceConfig {
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.oauth")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.staffdb")
	public DataSource staffdbDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "entityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource())
				.packages("org.octopus.dashboard.shared.data").build();
	}

	@Bean(name = "staffdbEntityManager")
	public LocalContainerEntityManagerFactoryBean staffdbEntityManagerFactory(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).packages("org.octopus.dashboard.data")
				.build();
	}
}
