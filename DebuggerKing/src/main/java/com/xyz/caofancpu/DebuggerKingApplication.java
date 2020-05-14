package com.xyz.caofancpu;

import com.alibaba.fastjson.parser.ParserConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.xyz.caofancpu"}, exclude = {SecurityAutoConfiguration.class})
//禁止自动注册
//@EnableDiscoveryClient
@MapperScan("com.xyz..*.mapper")
@EnableEncryptableProperties
public class DebuggerKingApplication {

    /**
     * 对象解析为JSONArray
     * 可能出现:  com.alibaba.fastjson.JSONException: autoType is not support ...
     * 解决办法: 1.配置JVM启动参数: -Dfastjson.parser.autoTypeAccept=序列化对象包根目录1. , 序列化对象包根目录2. 等
     *         2. 配置如下静态代码
     */
    static {
        ParserConfig.getGlobalInstance().addAccept("com.xyz.caofancpu.model");
    }

    public static void main(String[] args) {
        SpringApplication.run(DebuggerKingApplication.class, args);
    }
}
