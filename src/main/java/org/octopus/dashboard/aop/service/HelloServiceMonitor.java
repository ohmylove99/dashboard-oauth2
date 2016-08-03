/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.octopus.dashboard.aop.service;

import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloServiceMonitor {
	private final CounterService counterService;

	@Autowired
	public HelloServiceMonitor(CounterService counterService) {
		this.counterService = counterService;
	}

	@AfterThrowing(pointcut = "execution(* org.octopus.dashboard..*Service.*(..))", throwing = "e")
	public void afterGetGreetingThrowsException(NoSuchElementException e) {
		counterService.increment("counter.errors.get_greeting");
	}

	@AfterReturning("execution(* org.octopus.dashboard..*Service.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		System.out.println("Completed: " + joinPoint);
	}

}
