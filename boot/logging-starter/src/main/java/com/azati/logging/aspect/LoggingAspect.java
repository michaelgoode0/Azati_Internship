package com.azati.logging.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class.getName());

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.RestController)")
    public void executeTiming(){}

    @After("executeTiming()")
    public void logMethodCall(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Method {} of {} class executed with {} arguments", methodName, className, args);
    }

    @AfterReturning(value = "executeTiming()", returning = "result")
    public void logAfterReturning(Object result) {
        if(result != null){
            logger.info("Returning value: {}", result);
        }
        else {
            logger.info("Result is null");
        }
    }

    @Around("executeTiming()")
    public Object logMethodTiming(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTIme = System.currentTimeMillis();
        Object returnValue = proceedingJoinPoint.proceed();
        Long totalTime = System.currentTimeMillis() - startTIme;
        logger.info("Time taken by '{}' class is {} ms", proceedingJoinPoint.getSignature().getName(), totalTime);
        return returnValue;
    }
}
