package com.github.taven.rest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogTimeoutAspect {

    @Pointcut("execution(* com.github.taven.rest.controller..*.*())")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            long endTime = System.currentTimeMillis();
            long timeout = (endTime - startTime) / 1000;
            String message = "customNormalRequest timeout " + timeout + "s";
            log.info(message);
            return message;
        }
    }

}
