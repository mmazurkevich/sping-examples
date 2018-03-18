package com.example.spring.react.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class StreamingController {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> getStream() {
        return Flux.generate(() -> 0, (state, sink) -> {
            sink.next("Lol " + state);
            return state + 1;
        }).delayElements(Duration.ofSeconds(2));
    }
}
