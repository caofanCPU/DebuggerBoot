package com.xyz.caofancpu.utils;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * FileName: StandardThreadPoolUtil
 * Author:   caofanCPU
 * Date:     2018/9/7 16:58
 */

public class StandardThreadPoolUtil {
    
    private static volatile ThreadPoolTaskExecutor threadPoolTaskExecutor;
    
    private StandardThreadPoolUtil() {}
    
    public static ThreadPoolTaskExecutor getInstance() {
        if (threadPoolTaskExecutor == null) {
            synchronized (ThreadPoolTaskExecutor.class) {
                if (threadPoolTaskExecutor == null) {
                    threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
                }
            }
        }
        return threadPoolTaskExecutor;
    }
    
    private void init(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(16);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(64);
        //队列最大数
        threadPoolTaskExecutor.setQueueCapacity(16);
        //线程名称前缀
        threadPoolTaskExecutor.setThreadNamePrefix("debugger_");
        /**
         * rejection-policy：当pool已经达到max size的时候，如何处理新任务
         * CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
         * 对拒绝task的处理策略
         */
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        //加载
        threadPoolTaskExecutor.initialize();
    }
}
