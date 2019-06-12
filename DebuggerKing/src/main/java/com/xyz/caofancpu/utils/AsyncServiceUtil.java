package com.xyz.caofancpu.utils;

import com.xyz.caofancpu.message.kafka.KafkaMessage;
import com.xyz.caofancpu.message.kafka.KafkaSender;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * FileName: AsyncServiceUtil
 * Author:   caofanCPU
 * Date:     2018/11/17 16:56
 */
@Component
@Async(value = "standardThreadPool")
@Api(description = "异步服务中心")
public class AsyncServiceUtil {
    
    @Autowired
    private transient KafkaSender kafkaSender;
    
    public void sendKafkaMSG(KafkaMessage kafkaMessage) {
        kafkaSender.sendMessage(kafkaMessage);
    }
    
}
