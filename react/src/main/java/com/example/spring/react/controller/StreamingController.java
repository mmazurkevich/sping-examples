package com.example.spring.react.controller;

import com.example.spring.react.model.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
public class StreamingController {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> getStream() {
        return Flux.generate(() -> 0, (state, sink) -> {
            sink.next(new Message(LocalDateTime.now().toString()));
            if (state == 1000) sink.complete();
            return state + 1;
        }).delayElements(Duration.ofSeconds(2));
    }
}
