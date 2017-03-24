package com.spring.sample.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Mikhail on 24.03.2017.
 */
@Service
public class MessageProducer implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void run(String... strings) throws Exception {
        new Thread(this::postMessage).start();
    }

    private void postMessage() {
        logger.info("====Sending message ==== in thread {}", Thread.currentThread().getId());
        for (int i = 0; i < 20; i++) {
            jmsTemplate.convertAndSend("testExample", i);
        }
    }
}
