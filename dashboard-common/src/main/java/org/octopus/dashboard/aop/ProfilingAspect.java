package org.octopus.dashboard.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.octopus.dashboard.config.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class ProfilingAspect {
	private static Logger logger = LoggerFactory.getLogger(ProfilingAspect.class);

	@Autowired
	private Environment env;
	@Autowired
	private CounterService counterService;

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

	@Pointcut("within(org.octopus.dashboard..*repository..*) || within(org.octopus.dashboard..*service..*) || within(org.octopus.dashboard..*rest..*)")
	public void loggingPointcut() {
	}

	@AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		if (env.acceptsProfiles(ConfigConstants.SPRING_PROFILE_DEVELOPMENT)) {
			logger.error(
					"Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), e.getCause(), e.getMessage());
		}
		else {
			logger.error("Exception in {}.{}() with cause = {}",
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), e.getCause());
		}
	}

	@Around("loggingPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter: {}.{}() with argument[s] = {}",
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(),
					Arrays.toString(joinPoint.getArgs()));
		}
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			sw.start(joinPoint.getSignature().getName());
			Object result = joinPoint.proceed();
			if (logger.isDebugEnabled()) {
				logger.debug("Exit: {}.{}() with result = {}",
						joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), result);
			}
			return result;
		}
		catch (IllegalArgumentException e) {
			logger.error("Illegal argument: {} in {}.{}()",
					Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName());
			throw e;
		}
		finally {
			sw.stop();
			logger.debug(joinPoint + ": " + sw.shortSummary());
		}
	}

	// @formatter:off
	/*@Pointcut("execution(* org.octopus.dashboard..*Service*.*(..))")
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
	}*/
	// @formatter:on
}