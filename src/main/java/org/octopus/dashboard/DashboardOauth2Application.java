package org.octopus.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DashboardOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(DashboardOauth2Application.class, args);
	}
}
