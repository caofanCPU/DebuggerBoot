package com.xyz.caofancpu.init;

import com.xyz.caofancpu.message.kafka.KafkaMessage;
import com.xyz.caofancpu.message.kafka.KafkaSender;
import com.xyz.caofancpu.model.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.Map;

/**
 * @author caofanCPU
 */
//@Component
//@Order(value = 2)
public class KafkaRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(KafkaRunner.class);

    private final String testTopic = "testDemo";

//    private final String testConsumer = "testKafkaServiceImpl";

//    @Autowired
//    private ApplicationContext applicationContext;

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private KafkaProperty kafkaProperty;

//    @Autowired
//    private KafkaConsumerDispatcher kafkaConsumerDispatcher;

    @Override
    public void run(String... strings) {
        initKafkaProperty();
        logger.info(kafkaProperty.getTopicMap().toString());
        logger.info(Arrays.toString(kafkaProperty.getTopics()));
        sendMessage(testTopic, new Area(100, "oh, mykafka", 2));
//        kafkaConsumerDispatcher.dispatcher("{\"id\":\"12\"}");
    }

    /**
     * 初始化kafka监听主题
     * 必要性：强
     * 原因：kafkaProperty单例对象初始化与属性文件赋值操作不在一起，因而采用项目启动时再次初始化
     * 优化：先前苟且，后续优化
     */
    private void initKafkaProperty() {
        Map<String, String> topicMap = kafkaProperty.getTopicMap();
        if (!topicMap.isEmpty()) {
            kafkaProperty.setTopics(topicMap.keySet().toArray(kafkaProperty.getTopics()));
        }
    }

    /**
     * 启动测试
     * 从SpringFactory中根据beanName获取Bean实例
     */
//    private void testGetBeanFromSpringFactory() {
//        Object obj = applicationContext.getAutowireCapableBeanFactory().getBean(testConsumer);
//        TestKafkaServiceImpl bean = (TestKafkaServiceImpl) obj;
//        bean.handle(new KafkaMessage("haha", "OK"));
//    }

    /**
     * 发送消息到kafka
     */
    private void sendMessage(String topic, Object data) {
        kafkaSender.sendMessage(new KafkaMessage(topic, data));
    }

}
