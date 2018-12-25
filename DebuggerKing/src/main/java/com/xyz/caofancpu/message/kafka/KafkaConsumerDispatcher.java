package com.xyz.caofancpu.message.kafka;

import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.init.KafkaProperty;
import com.xyz.caofancpu.service.kafka.KafkaConsumerService;
import com.xyz.caofancpu.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by caofanCPU on 2018/8/3.
 */
@Component
public class KafkaConsumerDispatcher {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerDispatcher.class);
    
    @Autowired
    private KafkaProperty kafkaProperty;
    
    /**
     * 测试SpEL表达式
     */
    @Value("#{'${test.spring.kafka.topics}'.split(',')}")
    private String[] kafkaTopics;
    
    /**
     * 蛋疼！！！：org.springframework.kafka 1.1.8不支持SpEL表达式！！！！！！！！！！！！！！！！
     * <p>
     * 关于topics的配置
     * 初衷：只用一个监听器，监听所有的topic，即topics模糊全匹配，类似于*的功能
     * 1.从配置文件中引入变量的值：@KafkaListener(topics = "${kafka.topicMap.testDemo}")
     * 2.从bean中取得某属性值：@KafkaListener(topics = "#{kafkaProperty.topics}")
     * 即可通过SpEL表达式实现
     * 参考：https://docs.spring.io/spring-kafka/reference/htmlsingle/#kafka-listener-annotation
     *
     * @param data
     */
    
    @KafkaListener(topics = {"testDemo", "testDemo2", "testDemo3"})
    public void dispatcher(String data) {
        /** 从当前线程的堆栈中获取当前执行方法，也可获取执行类 */
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        final String currentMethodName = stack[1].getMethodName();
        final String topic = parseTopic(currentMethodName);
        Map<String, String> topicMap = kafkaProperty.getTopicMap();
        String handleClassName = topicMap.get(topic);
        if (handleClassName == null) {
            return;
        }
        Object bean = null;
        try {
            bean = SpringUtils.getBean(handleClassName);
        } catch (BeansException | IllegalStateException e) {
            logger.error("获取消息处理实例失败，{}", e.getMessage());
        }
        if (bean instanceof KafkaConsumerService) {
            KafkaConsumerService consumer = (KafkaConsumerService) bean;
            consumer.handle(convertKafkaMessage(data, topic));
        }
    }
    
    private final String parseTopic(final String methodName) {
        String topic = null;
        try {
            Method currentMethod = this.getClass().getMethod(methodName, String.class);
            KafkaListener annotation = currentMethod.getAnnotation(KafkaListener.class);
            topic = annotation.topics()[0];
        } catch (NoSuchMethodException e) {
            logger.error("获取kafka监听注解信息失败, {}", e.getMessage());
        }
        return topic;
    }
    
    private KafkaMessage convertKafkaMessage(String data, final String topic) {
        KafkaMessage kafkaMessage = null;
        if (StringUtils.isNotEmpty(data)) {
            try {
                kafkaMessage = JSONObject.parseObject(data, KafkaMessage.class);
                kafkaMessage.setTopic(topic);
            } catch (Exception e) {
                logger.error("转换kafka消息对象失败！[KafkaMessage]{}", e.getMessage());
            }
        }
        return kafkaMessage;
    }
    
}
