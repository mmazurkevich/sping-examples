package com.spring.example.stomp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    private String username;
    private String message;

    public Message(@JsonProperty("username") String username, @JsonProperty("message") String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
