package com.spring.example.concurrency.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

public class ReentrantReadWrite {

    private static int count;
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static StampedLock stampedLock = new StampedLock();

    private void semaphore() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Semaphore semaphore = new Semaphore(5);

        Runnable longRunningTask = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired");
                    sleep(5);
                } else {
                    System.out.println("Could not acquire semaphore");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10)
                .forEach(i -> executor.submit(longRunningTask));
    }

    private void stampedLock() {
        long l = stampedLock.readLock();
        stampedLock.unlockRead(l);
        l = stampedLock.writeLock();
        stampedLock.unlockWrite(l);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                Lock lock = readWriteLock.writeLock();
                lock.lock();
                count++;
                System.out.println(count);
                lock.unlock();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                Lock lock = readWriteLock.readLock();
                lock.lock();
                count++;
                lock.unlock();
            }
        }).start();
    }
}
