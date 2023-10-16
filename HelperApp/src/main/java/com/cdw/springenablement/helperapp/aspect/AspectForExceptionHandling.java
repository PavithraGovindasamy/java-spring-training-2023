package com.cdw.springenablement.helperapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectForExceptionHandling {

    private static final Logger logger = LoggerFactory.getLogger(AspectForExceptionHandling.class);

    @Before("execution(* com.cdw.springenablement.helperapp.controller.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("Method started: " + method);
    }



    @Around("execution(* com.cdw.springenablement.helperapp.controller.*.*(..))")
    public Object handleExceptionAndLogMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String method = proceedingJoinPoint.getSignature().toShortString();
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
            logger.info("Method finished: " + method);
        } catch (Exception exception) {
            logger.error("Exception occurred in method: " + method + ", Exception message: " + exception.getMessage());
            throw exception;
        }

        return result;
    }

}
