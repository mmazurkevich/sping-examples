package com.example.integration.rest.config;

import com.example.integration.controller.OrderController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Configuration
@EnableWebMvc
public class IntegrationContextConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(IntegrationContextConfiguration.class, args);
    }

    @Bean
    public OrderController orderController(){
        return new OrderController();
    }
}
