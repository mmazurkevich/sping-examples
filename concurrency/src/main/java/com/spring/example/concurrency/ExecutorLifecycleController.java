package com.spring.example.concurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ScheduledFuture;

@RestController
public class ExecutorLifecycleController {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private List<PoollerTask> poollerTasks;

    private List<ScheduledFuture<?>> scheduledFutures;

    @RequestMapping(path = "/start", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity startTasks(){
        poollerTasks = new ArrayList<>();
        scheduledFutures = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            PoollerTask poollerTask = new PoollerTask();
            poollerTasks.add(poollerTask);
        }
        poollerTasks.forEach(poollerTask -> {
            scheduledFutures.add(threadPoolTaskScheduler.schedule(poollerTask, poollerTask));
        });
        System.out.println("Task should be started");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/trigger", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity triggerTasks(){
        poollerTasks.forEach(poollerTask -> {
            poollerTask.setReconfig(true);
        });
        System.out.println("Task should be started");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/after", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity afterTasks(){
        poollerTasks.forEach(poollerTask -> {
            poollerTask.setReconfig(false);
            threadPoolTaskScheduler.schedule(poollerTask, poollerTask);
        });
        System.out.println("Task should be started");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getTasks(){
        System.out.println("awaiting for task complition " + threadPoolTaskScheduler.getActiveCount());
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(path = "/stopAll", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity stopTasks(){
        System.out.println("Start finishing tasks");
        scheduledFutures.forEach(scheduledFuture -> scheduledFuture.cancel(false));
        while (threadPoolTaskScheduler.getActiveCount() !=0){
            System.out.println("awaiting for task complition " + threadPoolTaskScheduler.getActiveCount());
        }
//        scheduledFutures.forEach(scheduledFuture -> {
//            try {
//                scheduledFuture.get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
        System.out.println("awaiting for task complition " + threadPoolTaskScheduler.getActiveCount());
        System.out.println("Finish all currently working tasks");
        return new ResponseEntity(HttpStatus.OK);
    }
}
