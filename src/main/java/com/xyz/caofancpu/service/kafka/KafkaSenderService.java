package com.xyz.caofancpu.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author caofanCPU
 * @date 2018-08-03
 */
@Service
public class KafkaSenderService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSenderService.class);
    
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 发送消息到kafka,主题为test
     */
    public void sendMessage(final String topic, String data){
        logger.info("send message starting...");
        kafkaTemplate.send(topic, data);
        logger.info("send message success!");
    }
}
