package com.spring.example.stomp.client;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class SessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/connection", new ConnectionHandler());
        session.subscribe("/topic/messages", new MessageHandler());
        session.send("/app/login", SocketClient.username);
    }
}
