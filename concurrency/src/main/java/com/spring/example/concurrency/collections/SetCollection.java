package com.spring.example.concurrency.collections;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class SetCollection {

    /**
     * ConcurrentSkipListSet store elements like natural order collection
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Set<Integer> set = new ConcurrentSkipListSet<>();
        //Init set in one thread
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0)
                set.add(i + 500);
            else
                set.add(i);
        }
        System.out.println(set);
        new Thread(() -> {
           for (Integer element : set) {
               System.out.println("Set : " + set);
               System.out.println("Set element: " + element);
               System.out.println("Set size: " + set.size());

           }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                if (i % 2 == 0)
                    set.add(i + 500);
                else
                    set.add(i);
            }
        }).start();
    }
}
