package com.spring.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@SpringBootApplication
@EnableScheduling
@EnableWebSocketMessageBroker
public class BootstrapApplication extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * Web site http://stomp.github.io/
     */
    public static void main(String[] args) {
        SpringApplication.run(BootstrapApplication.class,args);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat");
        registry.addEndpoint("/login");
        registry.addEndpoint("/logout");
//        registry.addEndpoint("/chat").withSockJS();
    }

}
