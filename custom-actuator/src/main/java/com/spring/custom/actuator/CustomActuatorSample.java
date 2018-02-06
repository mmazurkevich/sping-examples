package com.spring.custom.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Mikhail on 25.03.2017.
 */
@SpringBootApplication
@ManagementContextConfiguration
public class CustomActuatorSample {

    public static void main(String[] args) {
        SpringApplication.run(CustomActuatorSample.class, args);
    }
}
