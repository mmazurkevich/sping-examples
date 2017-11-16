package com.spring.aspect;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class RepeaterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepeaterAspect.class);

    private List<Class<? extends Throwable>> exceptionList;
    private int maxAttemptCount;

    @Around("@annotation(com.spring.aspect.Repeater)")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LOGGER.info("Repeater bind to {} method", methodSignature.toString());
        Method targetMethod = methodSignature.getMethod();
        Repeater annotation = targetMethod.getAnnotation(Repeater.class);
        this.maxAttemptCount = annotation.maxAttempts();
        this.exceptionList = Arrays.asList(annotation.value());
        LOGGER.info("Annotation values {}", annotation);
        if (maxAttemptCount > 0) {
            for (int i = 1; i <= maxAttemptCount; i++) {
                try {
                    return joinPoint.proceed();
                }catch (Throwable e){
                    LOGGER.warn("Repeater got an exception from the method call", e);
                    if (!isRecoverable(e) || i == maxAttemptCount){
                        LOGGER.warn("Exception is no recoverable or repeater reached max attempt count");
                        throw e;
                    }
                    LOGGER.info("Exception is recoverable, try to execute method again");
                }
            }
            return joinPoint.proceed();
        } else {
            LOGGER.debug("Max attempt count setted to equal or less then zero, do a direct method call");
            return joinPoint.proceed();
        }
    }

    private boolean isRecoverable(Throwable thr) {
        for (Throwable throwable :  ExceptionUtils.getThrowables(thr)){
            for (Class<? extends Throwable> zClass : exceptionList) {
                if (zClass.isInstance(throwable))
                    return true;
            }
        }
        return false;
    }
}
