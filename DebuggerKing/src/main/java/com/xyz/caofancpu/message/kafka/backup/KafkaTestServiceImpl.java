//package com.xyz.caofancpu.message.kafka.backup;
//
//import com.xyz.caofancpu.message.kafka.KafkaMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
///**
// * Created by caofanCPU on 2018/8/3.
// */
//@Service(value = "kafkaTestServiceImpl")
//public class KafkaTestServiceImpl implements KafkaTestService {
//
//    private static final Logger logger = LoggerFactory.getLogger(KafkaTestServiceImpl.class);
//
//    @Override
//    public void handleTestMessage(KafkaMessage kafkaMessage) {
//        logger.info("服务实现类kafkaTestServiceImpl处理消息, 消息主题： {}", kafkaMessage.getTopic());
//        logger.info("服务实现类kafkaTestServiceImpl处理消息, 消息内容： {}", kafkaMessage.getData());
//    }
//}
