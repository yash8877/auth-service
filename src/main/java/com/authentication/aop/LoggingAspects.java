package com.authentication.aop;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspects {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspects.class);

    @Around("execution(* com.authentication.service.AuthService.*(..))")
    public Object logAuthentication(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("Executing method: {}", methodName);
        if (args.length > 0) {
            logger.info("Arguments: {}", args[0]); // Log first argument (usually request)
        }

        Object result = joinPoint.proceed(); // Proceed with method execution

        logger.info("Method {} execution completed. Result: {}", methodName, result);
        return result;
    }
}
