package com.spring.custom.actuator;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;

/**
 * Created by Mikhail on 25.03.2017.
 */
public class ConditionalOnProfile implements Condition {

    private String conditionalProfile = "test";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return Arrays.stream(conditionContext.getEnvironment().getActiveProfiles())
                .filter(s -> s.equals(conditionalProfile)).findFirst().isPresent();
    }
}
