package com.xyz.caofancpu.util.multiThreadUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *
 */
public class DebuggerKingRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DebuggerKingRunnable.class);

    private Runnable task;
    private String taskDescription;
    private ConcurrentLinkedDeque<String> concurrentLinkedDeque;

    public DebuggerKingRunnable(Runnable task) {
        this.task = task;
    }

    public DebuggerKingRunnable(String taskDescription, ConcurrentLinkedDeque<String> concurrentLinkedDeque) {
        this.taskDescription = taskDescription;
        this.concurrentLinkedDeque = concurrentLinkedDeque;
    }

    @Override
    public void run() {
        begin();
        try {
            handle();
        } catch (Exception e) {
            logger.error("error...", e);
        } finally {
            after();
        }
    }

    private void begin() {
        System.out.println(taskDescription);
    }

    private void handle() {
        concurrentLinkedDeque.add(taskDescription);
    }

    private void after() {

    }

}
