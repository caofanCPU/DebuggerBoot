package com.xyz.caofancpu.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author caofanCPU
 * @date 2018-08-03
 */
@Component
@Order(value = 2)
public class KafkaRunner implements CommandLineRunner {
    
    private final Logger logger = LoggerFactory.getLogger(KafkaRunner.class);
    
    @Autowired
    private KafkaTemplate kafkaTemplate;
    
    public static final String TEST_TOPIC = "caofancpu_kafka";
    
    @Override
    public void run(String... strings) {
        logger.info("kafka连接中...");
        sendMessage(TEST_TOPIC, "oh, my kafka!");
    }
    
    /**
     * 发送消息到kafka,主题为test
     */
    public void sendMessage(String topic, String data){
        kafkaTemplate.send(topic, data);
        logger.info("已经发送kafka消息，待接收");
    }
    
    /**
     * 监听test主题,有消息就读取
     * @param message
     */
    @KafkaListener(topics = TEST_TOPIC)
    public void creditOkConsumer(String message){
        logger.info("已接收kafka消息，[{}]", message);
        logger.info("kafka连接完成，配置成功！");
    }
    
}
