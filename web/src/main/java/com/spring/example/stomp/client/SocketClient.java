package com.spring.example.stomp.client;

import com.spring.example.stomp.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public class SocketClient {

    public static String username;
    public static StompSession connection;

    /**
     * http://www.baeldung.com/websockets-api-java-spring-client
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Enter your username: ");
        Scanner scanner = new Scanner(System.in);
        username = scanner.nextLine();

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSessionHandler sessionHandler = new SessionHandler();
        connection = stompClient.connect("ws://localhost:8088/login", sessionHandler)
                                .get();

        String message;
        while (!(message = scanner.nextLine()).equals("exit")) {
            connection.send("/app/chat", new Message(username, message));
        }
        connection.send("/app/logout", username);
    }
}
