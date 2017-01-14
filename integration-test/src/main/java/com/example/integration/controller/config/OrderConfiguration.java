package com.example.integration.controller.config;

import com.example.integration.controller.OrderController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Mikhail on 14.01.2017.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = OrderController.class)
public class OrderConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(OrderConfiguration.class, args);
    }
}
