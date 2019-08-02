package com.xyz.caofancpu.utils;

import com.xyz.caofancpu.message.kafka.KafkaMessage;
import com.xyz.caofancpu.message.kafka.KafkaSender;
import com.xyz.caofancpu.util.multiThreadUtils.DebuggerKingRunnable;
import com.xyz.caofancpu.util.multiThreadUtils.DemoRunnableTask;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * FileName: AsyncServiceUtil
 * Author:   caofanCPU
 * Date:     2018/11/17 16:56
 */
@Component
@Async(value = "standardThreadPool")
@Api(description = "异步服务中心")
public class AsyncServiceUtil {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceUtil.class);

    @Resource(name = "standardThreadPool")
    private ThreadPoolTaskExecutor pool;

    @Autowired
    private transient KafkaSender kafkaSender;

    public void sendKafkaMSG(KafkaMessage kafkaMessage) {
        kafkaSender.sendMessage(kafkaMessage);
    }

    @Async
    public void execute(Object... objects) {
        begin(objects);
        try {
            handle(objects);
        } catch (Exception e) {
            this.handle(new Object(), e);
        } finally {
            after(objects);
        }
    }

    private void begin(Object... objects) {
        // do something
    }

    private void handle(Object... objects) {
        // do something
        int a = 1;
        switch (a) {
            case 0:
                pool.execute(() -> System.out.println(0));
                break;
            case 1:
                pool.execute(new DebuggerKingRunnable(new DemoRunnableTask(1, new CountDownLatch(3))));
                break;
            default:
                break;
        }
    }

    private void after(Object... objects) {
        // do something
    }

    private void error(Object object, Exception e) {
        logger.error("error...");
        // do something, for example, push error message into Redis by Queue
    }
}
