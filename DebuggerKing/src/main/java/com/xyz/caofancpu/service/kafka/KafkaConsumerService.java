package com.xyz.caofancpu.service.kafka;

import com.xyz.caofancpu.message.kafka.KafkaMessage;

/**
 * Created by caofanCPU on 2018/8/3.
 */
public interface KafkaConsumerService {
    
    Boolean handle(KafkaMessage kafkaMessage);
    
}
