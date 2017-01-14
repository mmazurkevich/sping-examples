package com.example.integration.controller.config;

import com.example.integration.controller.OrderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Mikhail on 14.01.2017.
 */
@Configuration
@EnableWebMvc
@Profile("test")
public class IntegrationContextConfiguration {

    @Bean
    public OrderController orderController(){
        return new OrderController();
    }
}
