package com.spring.example.stomp.server;

import com.spring.example.stomp.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalTime;

@Controller
public class SocketServer {


    @MessageMapping("/login")
    @SendTo("/topic/connection")
    public String login(@Payload String username, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", username);
        return LocalTime.now() + " Hello: " + username;
    }

    @MessageMapping("/logout")
    @SendTo("/topic/connection")
    public String logout(@Payload String username) throws Exception {
        return LocalTime.now() + " User: " + username + " left the chat";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String sendMessage(@Payload Message message) throws Exception {
        return LocalTime.now() + " Message from " + message.getUsername() + ": " + message.getMessage();
    }

}
