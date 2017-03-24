package com.spring.sample.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by Mikhail on 24.03.2017.
 */
@Component
public class MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @JmsListener(destination = "testExample")
    public void consumerOne(String msg) {
        logger.info("Consumer 1: {} IN thread: {}", msg, Thread.currentThread().getId());
    }

    @JmsListener(destination = "testExample")
    public void consumerTwo(String msg) {
        logger.info("Consumer 2: {} IN thread: {}", msg, Thread.currentThread().getId());
    }
}
