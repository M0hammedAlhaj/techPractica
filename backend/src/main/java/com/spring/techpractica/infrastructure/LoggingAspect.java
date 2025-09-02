package com.spring.techpractica.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final ThreadLocal<Long> requestStartTime = new ThreadLocal<>();
    private final ThreadLocal<Map<String, Long>> methodTimes = ThreadLocal.withInitial(HashMap::new);

    @Around("execution(* com.spring.techpractica.UI..*(..)) || execution(* com.spring.techpractica.Application..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (requestStartTime.get() == null) {
            requestStartTime.set(System.currentTimeMillis());
        }

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        String methodName = joinPoint.getSignature().toShortString();
        methodTimes.get().put(methodName, duration);

        return result;
    }

    @After("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logTotalTime(JoinPoint joinPoint) {
        long totalTime = System.currentTimeMillis() - requestStartTime.get();

        String endpoint = joinPoint.getSignature().getDeclaringTypeName();

        log.info("==== Request Summary for [{}] ====", endpoint);
        methodTimes.get().forEach((method, time) ->
                log.info("Method {} took {} ms", method, time)
        );
        log.info("TOTAL time: {} ms", totalTime);
        log.info("=================================");

        requestStartTime.remove();
        methodTimes.remove();
    }
}
