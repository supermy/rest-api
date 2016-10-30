package com.supermy.base.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by moyong on 16/10/26.
 */
@Aspect
@Component
public class MyLogger {

    /** Handle to the log file */
    private final Log log = LogFactory.getLog(getClass());

    public MyLogger () {}

    @Pointcut("execution(* org.springframework.data.jpa..*.*(..)) or execution(* org.springframework.data.rest.webmvc.RepositorySearchController.*(..))")
    public void pointCut() {
    }

    @AfterReturning(pointcut = "pointCut()", returning = "returnVal")
    public void logMethodAccessAfter(JoinPoint joinPoint, Object returnVal) {
        System.out.println("*****返回值: " + returnVal + " *****");

        for (Object o:joinPoint.getArgs()) {
            System.out.println("*****参数: " + o + " *****");
        }

        log.info("***** Completed: " + joinPoint.getSignature().getName() + " *****");
        System.out.println("***** Completed: " + joinPoint.getSignature().getName() + " *****");
    }

    @Before("execution(* org.springframework.data.jpa..*.*(..))")
    public void logMethodAccessBefore(JoinPoint joinPoint) {

        for (Object o:joinPoint.getArgs()) {
            System.out.println("*****参数: " + o + " *****");
        }

        log.info("***** Starting: " + joinPoint.getSignature().getName() + " *****");
        System.out.println("***** Starting: " + joinPoint.getSignature().getName() + " *****");
    }
}