package com.example.spring.react.service;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReactiveService {

//    http://www.baeldung.com/reactor-core
    public static void main(String[] args) {
        Flux<String> flux = Flux.just("One", "Two", "Three");
        Flux.range(5, 3)
                .subscribe(System.out::println);
        Flux.fromIterable(Stream.of("One", "Two", "Three").collect(Collectors.toList()))
                .subscribe(System.out::println);
        Flux.range(1, 40)
//                .map(i -> {
//                    if (i <= 3) return i;
//                    throw new RuntimeException("Got to 4");
//                })
//                .publishOn(Schedulers.parallel())
//                .subscribeOn(Schedulers.parallel(), )
                .subscribe(System.out::println,
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Done"));
    }
}
