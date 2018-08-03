package com.xyz.caofancpu.message.kafka;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by caofanCPU on 2018/8/3.
 */
@Aspect
@Component
public class KafkaConsumerAspect {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerAspect.class);
    
    @Around("execution(* com.xyz..*.kafka.KafkaConsumer.*(..))")
    public void handle(ProceedingJoinPoint point) throws Throwable {
        logger.info("转换kafka消息");
        doHandle(point);
//        // 通过校验，继续执行原有方法
//        point.proceed();
        logger.info("处理完毕");
    }
    
    private void doHandle(ProceedingJoinPoint point) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        /** 1.在切点方法处获取方法名 */
        Method method = getMethod(point);
        /** 2.获取切点方法指定的注解 */
        HandleKafka annotation = method.getAnnotation(HandleKafka.class);
        KafkaListener klAnnotation = method.getAnnotation(KafkaListener.class);
        convertKafkaMessage(point.getArgs(), klAnnotation.topics().toString());
        /** 3.通过反射获取执行类的临时实例，执行方法 */
        Class<?> clazz = Class.forName(annotation.classFullName());
        Method handleMethod = clazz.getMethod(annotation.methodName(), KafkaMessage.class);
        handleMethod.invoke(clazz.newInstance(), point.getArgs());
    }
    
    private void convertKafkaMessage(Object[] args, final String topic) {
        if (args == null || args.length != 1) {
            return;
        }
        if (args[0] instanceof String) {
            String message = (String) args[0];
            try {
                KafkaMessage kafkaMessage = JSONObject.parseObject(message, KafkaMessage.class);
                kafkaMessage.setTopic(topic);
                args[0] = kafkaMessage;
            } catch (Exception e) {
                logger.error("转换kafka消息对象失败！[KafkaMessage]{}", e.getMessage());
            }
        }
    }
    
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint
                        .getTarget()
                        .getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(),
                                method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                logger.error("反射获取方法失败，{}" + e.getMessage());
            }
        }
        return method;
    }
    
}
