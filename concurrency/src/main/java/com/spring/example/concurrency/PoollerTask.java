package com.spring.example.concurrency;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

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

    public void setReconfig(boolean reconfig){
        this.reconfig = reconfig;
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        if (!reconfig) {
            return  new Date();
        }else {
            return null;
        }
    }
}
