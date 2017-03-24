package com.spring.custom.actuator;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Mikhail on 25.03.2017.
 */
@ConfigurationProperties(prefix = "custom.endpoint")
public class CustomActuatorProperties {

    private boolean enabled = false;
    private String logFilePath;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
}
