package org.octopus.dashboard.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiRestMonitor {
	private static final Logger logger = LoggerFactory.getLogger(ApiRestMonitor.class);

	@AfterReturning("execution(* org.octopus.dashboard..*Rest*.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		logger.debug("Completed: " + joinPoint);
	}

}
