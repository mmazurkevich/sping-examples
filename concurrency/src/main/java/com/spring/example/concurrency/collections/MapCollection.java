package com.spring.example.concurrency.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapCollection {

    public static void main(String[] args) {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        //Init map in one thread
        for (int i = 0; i < 200; i++) {
            map.put(String.valueOf(i), i);
        }
        new Thread(() -> {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
/*                Console console = System.console();
                console.writer().println();*/
                System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {  }
            }
            System.out.println("Map size: " + map.size());
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                map.put(String.valueOf(i + 500), i + 500);
            }
        }).start();
    }
}
