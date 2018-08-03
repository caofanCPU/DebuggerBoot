package com.xyz.caofancpu.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author caofanCPU
 * @date 2018-08-03
 */
@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    
    /**
     * 监听topic，读取消息
     * 读取即消费，类似UDP
     * @param message
     */
    @KafkaListener(topics = {"demo_xx"})
    public void creditOkConsumer(String message){
        logger.info("test topic message : {}", message);
    }

}
