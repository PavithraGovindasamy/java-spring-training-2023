package com.cdw.springenablement.helperapp.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect file for logging and handling the error
 */
@Aspect
@Component
public class AspectForExceptionHandling {

    private static final Logger logger = LoggerFactory.getLogger(AspectForExceptionHandling.class);

    /**
     * Method which runs before and around the method
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */

    @Around("execution(* com.cdw.springenablement.helperapp.controller.*.*(..))")
    public Object handleExceptionAndLogMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String method = proceedingJoinPoint.getSignature().toShortString();
        logger.info("Method started: " + method);

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
