package org.octopus.dashboard.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class ProfilingAspect {
	private static Logger logger = LoggerFactory.getLogger(ProfilingAspect.class);

	private final CounterService counterService;

	@Autowired
	public ProfilingAspect(CounterService counterService) {
		this.counterService = counterService;
	}

	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			sw.start(pjp.getSignature().getName());
			return pjp.proceed();
		}
		finally {
			sw.stop();
			logger.debug(pjp + ": " + sw.shortSummary());
		}
	}

	@Pointcut("execution(* org.octopus.dashboard..*Service*.*(..))")
	public void serviceMethodsToBeProfiled() {
	}

	@Pointcut("execution(* org.octopus.dashboard..*Rest*.*(..))")
	public void restMethodsToBeProfiled() {
	}

	@Around("serviceMethodsToBeProfiled()")
	public Object profileService(ProceedingJoinPoint pjp) throws Throwable {
		return profile(pjp);
	}

	@AfterReturning("serviceMethodsToBeProfiled()")
	public void logService(JoinPoint joinPoint) {
		counterService.increment("counter.api");
		logger.debug("Completed: " + joinPoint);
	}

	@Around("restMethodsToBeProfiled()")
	public Object profileRest(ProceedingJoinPoint pjp) throws Throwable {
		return profile(pjp);
	}

	@AfterReturning("restMethodsToBeProfiled()")
	public void logRest(JoinPoint joinPoint) {
		counterService.increment("counter.api");
		logger.debug("Completed: " + joinPoint);
	}
}