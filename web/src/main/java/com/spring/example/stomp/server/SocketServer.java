package com.spring.example.stomp.server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalTime;

@Controller
public class SocketServer {


    @MessageMapping("/login")
    @SendTo("/topic/connection")
    public String login(String username) throws Exception {
        return LocalTime.now() + " Hello: " + username;
    }

    @MessageMapping("/logout")
    @SendTo("/topic/connection")
    public String logout(String username) throws Exception {
        return LocalTime.now() + " User: " + username + " left the chat";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String sendMessage(String message) throws Exception {
        String[] split = message.split("#");
        return LocalTime.now() + " Message from " + split[0] + ": " + split[1];
    }

}
