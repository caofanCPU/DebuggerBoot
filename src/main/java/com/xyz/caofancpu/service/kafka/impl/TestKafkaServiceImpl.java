package com.xyz.caofancpu.service.kafka.impl;

import com.xyz.caofancpu.message.kafka.KafkaMessage;
import com.xyz.caofancpu.service.kafka.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by caofanCPU on 2018/8/3.
 */
@Service(value = "testKafkaServiceImpl")
@DependsOn("initContextPropertyInitializer")
public class TestKafkaServiceImpl implements KafkaConsumerService {
    
    private static final Logger logger = LoggerFactory.getLogger(TestKafkaServiceImpl.class);
    
    @Value("${wx.domain}")
    private String wxDomain;
    
    @Override
    public Boolean handle(KafkaMessage kafkaMessage) {
        logger.info("服务实现类kafkaTestServiceImpl处理消息, 消息主题： {}", kafkaMessage.getTopic());
        logger.info("服务实现类kafkaTestServiceImpl处理消息, 消息内容： {}", kafkaMessage.getData());
        logger.info("使用注解注入数据库配置变量，wx.domain = [{}]", wxDomain);
        return Boolean.TRUE;
    }
}
