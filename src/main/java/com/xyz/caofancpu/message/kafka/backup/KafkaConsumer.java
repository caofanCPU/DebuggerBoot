package com.xyz.caofancpu.message.kafka.backup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by caofanCPU on 2018/8/3.
 */
@Component
public class KafkaConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    
//    @HandleKafka(classFullName = "com.xyz.caofancpu.service.kafka.impl.KafkaTestServiceImpl",
//            methodName = "handleTestMessage")
//    @KafkaListener(topics = "handleTestMessage")
    public void handleTestMessage(String data) {
    }

}

