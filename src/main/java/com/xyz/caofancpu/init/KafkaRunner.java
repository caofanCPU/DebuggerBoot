package com.xyz.caofancpu.init;

import com.xyz.caofancpu.message.kafka.KafkaProperty;
import com.xyz.caofancpu.message.kafka.KafkaMessage;
import com.xyz.caofancpu.message.kafka.KafkaSender;
import com.xyz.caofancpu.model.Area;
import com.xyz.caofancpu.service.kafka.impl.TestKafkaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
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
    
    @Autowired
    private KafkaProperty kafkaProperty;
    
    private final String testTopic = "testDemo";
    
    private final String testConsumer = "testKafkaServiceImpl";
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Override
    public void run(String... strings) {
        logger.info(kafkaProperty.getTopicMap().toString());
        sendMessage(testTopic, new Area(100, "oh,mykafka", 2));
        Object obj = applicationContext.getAutowireCapableBeanFactory().getBean(testConsumer);
        TestKafkaServiceImpl bean = (TestKafkaServiceImpl) obj;
        bean.handle(new KafkaMessage("haha", "OK"));
    }
    
    /**
     * 发送消息到kafka
     */
    public void sendMessage(String topic, Object data){
        kafkaSender.sendMessage(new KafkaMessage(topic, data));
    }
    
}
