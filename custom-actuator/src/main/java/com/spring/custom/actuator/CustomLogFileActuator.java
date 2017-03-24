package com.spring.custom.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 * Created by Mikhail on 25.03.2017.
 */
@Component
@ConditionalOnProperty(prefix = "custom.endpoint", name = "enabled", havingValue = "true", matchIfMissing = false)
public class CustomLogFileActuator extends AbstractEndpoint<FileSystemResource> {

    @Value("${custom.endpoint.log-file-path}")
    private String logFilePath;

    public CustomLogFileActuator() {
        super("customlogfile");
    }

    public FileSystemResource invoke() {
        return new FileSystemResource(logFilePath);
    }
}