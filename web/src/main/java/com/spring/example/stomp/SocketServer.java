package com.spring.example.stomp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketServer {


    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String greeting(String message) throws Exception {
        System.out.println("Got a message from socket " + message);
        Thread.sleep(1000); // simulated delay
        return "Hello, " + message;
    }

}
