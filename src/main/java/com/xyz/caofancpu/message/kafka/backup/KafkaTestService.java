package com.xyz.caofancpu.message.kafka.backup;

import com.xyz.caofancpu.message.kafka.KafkaMessage;

/**
 * Created by caofanCPU on 2018/8/3.
 */
public interface KafkaTestService {
    
    /**
     * kafka消息处理DEMO
     * @param kafkaMessage
     */
    void handleTestMessage(KafkaMessage kafkaMessage);
    
}
