package com.westjet.middleware.roomservices.logging;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerLoggingAspect {
    Logger log = LoggerFactory.getLogger(ControllerLoggingAspect.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) "
            + "&& @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void restEndPoints() {
    }

    @Before("restEndPoints()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering Method: " + joinPoint.getSignature().getName());
        log.debug("Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "restEndPoints()", returning = "result")
    public void logAfterReturn(JoinPoint joinPoint, Object result) {
        log.info("Exiting Method: " + joinPoint.getSignature().getName());
        if (log.isDebugEnabled()) {
            try {
                log.debug("Response: " + mapper.writeValueAsString(result));
            } catch (JsonProcessingException e) {
                log.warn("An error occurred while attempting to write value as JSON: " + result.toString());
                log.warn(e.getMessage(), e);
            }
        }
    }

    @AfterThrowing(pointcut = "restEndPoints()", throwing = "t")
    public void logAfterException(JoinPoint joinPoint, Throwable t) {
        log.debug("Exception occurred in method: " + joinPoint.getSignature().getName());
        log.debug("Exception: " + t.getMessage(), t);
    }
}
