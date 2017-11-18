package com.spring.aspect;

import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * Created by Mikhail on 19.11.2017.
 */
@Component
public class RepeaterService {

    @Repeater
    public boolean repeaterSuccessfully() {
        System.out.println(Stream.iterate(1, a -> a * a).limit(1000).count());
        return true;
    }

    @Repeater(value = IllegalArgumentException.class, maxAttempts = 5)
    public void repeaterReachAttemptCount() {
        throw  new IllegalArgumentException();
    }


    @Repeater(IllegalArgumentException.class)
    public void repeaterFailed() {
        throw  new NullPointerException();
    }

}
