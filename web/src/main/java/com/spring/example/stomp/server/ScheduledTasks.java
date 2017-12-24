package com.spring.example.stomp.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ScheduledTasks {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedDelay = 30000)
    public void rescheduleTask(){
        messagingTemplate.convertAndSend("/topic/messages", LocalTime.now() + " Connection established");
    }
}
