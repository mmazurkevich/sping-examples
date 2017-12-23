package com.spring.example.stomp;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class CustomeStompSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/messages", this);
        session.send("/app/chat", "Tralala");
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("sdfsdfsd");
        String msg = (String) payload;
        System.out.println("Received : " + msg + " from :");
    }

}
