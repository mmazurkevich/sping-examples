package com.spring.example.stomp;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;


public class SocketStompClient {

    /**
     * http://www.baeldung.com/websockets-api-java-spring-client
     */
    public static void main(String[] args) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSessionHandler sessionHandler = new CustomeStompSessionHandler();
        stompClient.connect("ws://localhost:8088/chat", sessionHandler);

        new Scanner(System.in).nextLine(); // Don't close immediately.
    }
}
