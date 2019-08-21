package com.xyz.caofancpu.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * FileName: VisibleThreadPoolTaskExecutorHelper
 * Author:   caofanCPU
 * Date:     2018/11/17 16:47
 */
@Slf4j
public class VisibleThreadPoolTaskExecutorHelper extends ThreadPoolTaskExecutor {

    private void showThreadPoolInfo(String prefix) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        if (Objects.isNull(threadPoolExecutor)) {
            return;
        }
        log.info("\n线程前缀:[{}]\n执行动作:[{}]\n任务总数:[{}]\n已完成任务数:[{}]\n当前处理任务数量[{}]\n待办任务队列数量[{}]",
                this.getThreadNamePrefix(),
                prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size()
        );
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo("HandlingTask(处理)");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPoolInfo("HandlingTask(处理)");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo("SubmitTask(提交)");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo("SubmitTask(提交)");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPoolInfo("SubmitTask(监听提交)");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPoolInfo("SubmitTask(监听提交)");
        return super.submitListenable(task);
    }

}
