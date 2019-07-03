package com.xyz.caofancpu.thread.task;

import com.xyz.caofancpu.util.dataOperateUtils.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class DebuggerKingRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DebuggerKingRunnable.class);

    private String globalOnlyId;
    private Runnable runnableTask;

    public DebuggerKingRunnable(Runnable runnableTask) {
        this.globalOnlyId = String.valueOf(NumberUtil.getInteger(50));
        this.runnableTask = runnableTask;
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

    }

    private void handle() {

    }

    private void after() {

    }

}
