package com.xyz.caofancpu.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * Copyright (C), 2000-2018, 帝八哥科技无限股份有限公司
 * FileName: StandardThreadPoolUtilTest
 * Author:   CY_XYZ
 * Date:     2018/9/9 21:56
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class StandardThreadPoolUtilTest {
    
    private static final Logger logger = LoggerFactory.getLogger(StandardThreadPoolUtilTest.class);
    
    @Test
    public void getInstance()
            throws Exception {
        // 1.获取固定线程池单例
        ThreadPoolTaskExecutor threadPoolTaskExecutor = StandardThreadPoolUtil.getInstance();
        // 2.设定次线程数目
        final int threadNum = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            threadPoolTaskExecutor.execute(() -> {
                logger.info("次线程[" + Thread.currentThread().getName() + "]正在执行...");
                int sum = IntStream.rangeClosed(1, 100).sum();
                logger.info("高斯和：{}", sum);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        logger.info("激活最大线程数：{}", threadPoolTaskExecutor.getMaxPoolSize());
        threadPoolTaskExecutor.shutdown();
        logger.info("主线程运行结束");
    }
}