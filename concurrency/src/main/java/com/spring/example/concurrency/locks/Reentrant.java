package com.spring.example.concurrency.locks;

import java.util.concurrent.locks.ReentrantLock;

public class Reentrant {

    private static int count;
    private static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increaseValue();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increaseValue();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increaseValue();
            }
        }).start();
    }

    private static void increaseValue() {
        lock.lock();
        try {
            count++;
            System.out.println("Count value: " + count);
            System.out.println("Lock queue length: " + lock.getQueueLength());
            //TODO:: Show count thread that acquired this lock
            System.out.println("Lock hold count: " + lock.getHoldCount());
        } finally {
            lock.unlock();
        }
    }
}
