package com.spring.example.concurrency;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PoollerTask implements Runnable, Trigger{

    private boolean reconfig = false;

    @Override
    public void run() {
        System.out.println("Start new thread");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish thread");
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        if (reconfig) {
            LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(10);
            Date convertedDatetime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            reconfig = false;
            return convertedDatetime;
        }else {
            LocalDateTime localDateTime = LocalDateTime.now();
            Date convertedDatetime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            reconfig = true;
            return convertedDatetime;
        }
    }
}
