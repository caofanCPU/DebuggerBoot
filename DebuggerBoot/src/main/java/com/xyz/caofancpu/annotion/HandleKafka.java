package com.xyz.caofancpu.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by caofanCPU on 2018/8/3.
 * 功能：指定kafka消费者处理
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RUNTIME)
public @interface HandleKafka {
    
    /**
     * 处理类全路径名称
     *
     * @return
     */
    String classFullName();
    
    /**
     * 处理方法名称
     *
     * @return
     */
    String methodName();
}
