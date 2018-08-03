package com.xyz.caofancpu.init;

import com.xyz.caofancpu.message.kafka.KafkaMessage;
import com.xyz.caofancpu.message.kafka.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
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
    private KafkaSender kafkaSender;
    
    public static final String TEST_TOPIC = "caofancpu_kafka";
    
    @Override
    public void run(String... strings) {
        logger.info("kafka连接中...");
        sendMessage(TEST_TOPIC, "oh, my kafka!");
    }
    
    /**
     * 发送消息到kafka
     */
    public void sendMessage(String topic, String data){
        kafkaSender.sendMessage(new KafkaMessage(topic, data));
    }
    
    /**
     * 监听消息，读取即消费
     * @param message
     */
    @KafkaListener(topics = TEST_TOPIC)
    public void handleMessage(String message) {
        logger.info("已接收kafka消息，[{}]", message);
        logger.info("kafka连接完成，配置成功！");
    }
    
}
