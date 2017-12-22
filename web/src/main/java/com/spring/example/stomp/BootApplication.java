package com.spring.example.stomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {

    /**
     * Web site http://stomp.github.io/
     */
    public static void main(String[] args) {
        SpringApplication.run(com.spring.example.rest.BootApplication.class,args);
    }

}
