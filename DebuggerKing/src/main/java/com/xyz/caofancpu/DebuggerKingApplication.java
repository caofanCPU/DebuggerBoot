package com.xyz.caofancpu;

import com.alibaba.fastjson.parser.ParserConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.xyz.caofancpu"})
//禁止自动注册
//@EnableDiscoveryClient
@MapperScan("com.xyz..*.mapper")
@EnableEncryptableProperties
@EnableSwagger2
public class DebuggerKingApplication {

    /**
     * 对象解析为JSONArray
     * 可能出现:  com.alibaba.fastjson.JSONException: autoType is not support ...
     * 解决办法: 1.配置JVM启动参数: -Dfastjson.parser.autoTypeAccept=序列化对象包根目录1. , 序列化对象包根目录2. 等
     *         2. 配置如下静态代码
     */
    static {
        ParserConfig.getGlobalInstance().addAccept("com.ovfintech.bynz.model");
    }

    public static void main(String[] args) {
        SpringApplication.run(DebuggerKingApplication.class, args);
    }

//    /**
//     * 注意：@LoadBalanced注解，使用该注解，则调用其他服务时，必须使用服务名称[http://SERVICE-XXX]，而非IP:port
//     * 使用场景: 都在网关内部, 走ribbon调用
//     *
//     * @return
//     */
//    @Bean
//    @LoadBalanced
//    RestTemplate restTemplate() {
//        return restTemplateBuilder.build();
//    }
}
