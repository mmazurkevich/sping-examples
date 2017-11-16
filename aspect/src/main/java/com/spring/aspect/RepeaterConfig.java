package com.spring.aspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Mikhail on 16.11.2017.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class RepeaterConfig {

    public static void main(String[] args) {
        SpringApplication.run(RepeaterConfig.class, args);
    }
}
