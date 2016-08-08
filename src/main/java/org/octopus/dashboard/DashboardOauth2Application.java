package org.octopus.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
@EnableScheduling
public class DashboardOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(DashboardOauth2Application.class, args);
	}
}
