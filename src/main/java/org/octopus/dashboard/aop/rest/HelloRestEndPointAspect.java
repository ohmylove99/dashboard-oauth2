package org.octopus.dashboard.aop.rest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloRestEndPointAspect {
	private final CounterService counterService;

	@Autowired
	public HelloRestEndPointAspect(CounterService counterService) {
		this.counterService = counterService;
	}

	@AfterThrowing(pointcut = "execution(* org.octopus.dashboard..*HomeRestEndPoint.*(..))", throwing = "e")
	public void afterGetGreetingThrowsException(Exception e) {
		counterService.increment("counter.errors.hellorest");
	}

	@AfterReturning("execution(* org.octopus.dashboard..*HomeRestEndPoint.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		counterService.increment("counter.hellorest");
	}
}
