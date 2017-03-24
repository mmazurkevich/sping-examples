package com.spring.custom.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

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
