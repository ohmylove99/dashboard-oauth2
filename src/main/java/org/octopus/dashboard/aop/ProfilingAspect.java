package org.octopus.dashboard.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.octopus.dashboard.api.rest.HelloRestEndPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class ProfilingAspect {
	private static Logger logger = LoggerFactory.getLogger(HelloRestEndPoint.class);

	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			sw.start(pjp.getSignature().getName());
			return pjp.proceed();
		} finally {
			sw.stop();
			logger.debug(pjp + ": " + sw.shortSummary());
		}
	}

	@Around("serviceMethodsToBeProfiled()")
	public Object profileService(ProceedingJoinPoint pjp) throws Throwable {
		return profile(pjp);
	}

	@Pointcut("execution(* org.octopus.dashboard..*Service*.*(..))")
	public void serviceMethodsToBeProfiled() {
	}

	@Around("restMethodsToBeProfiled()")
	public Object profileRest(ProceedingJoinPoint pjp) throws Throwable {
		return profile(pjp);
	}

	@Pointcut("execution(* org.octopus.dashboard..*Rest*.*(..))")
	public void restMethodsToBeProfiled() {
	}
}