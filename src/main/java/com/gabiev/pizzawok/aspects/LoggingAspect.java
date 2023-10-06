package com.gabiev.pizzawok.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Autowired
    @Qualifier("myTestLogger")
    private Logger logger;

    @Pointcut("execution(* com.gabiev.pizzawok.controllers.*.*(..))")
    private void allMethodsFromControllers() {}

    @AfterThrowing(pointcut = "allMethodsFromControllers()", throwing = "e")
    public void afterThrowingAllMethodsFromControllers(Throwable e) {
        logger.error(String.format("Error message: \"%s\"    %s", e.getMessage(), e.getStackTrace()[0].toString()));
    }
}
