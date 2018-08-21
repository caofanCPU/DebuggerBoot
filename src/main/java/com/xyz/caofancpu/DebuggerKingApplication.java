package com.xyz.caofancpu;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = {"com.xyz.caofancpu"})
//禁止自动注册
//@EnableDiscoveryClient
@MapperScan("com.xyz.caofancpu.mapper")
@EnableEncryptableProperties
@EnableSwagger2
public class DebuggerKingApplication {
    
    @Resource
    RestTemplateBuilder restTemplateBuilder;
    
    public static void main(String[] args) {
        SpringApplication.run(DebuggerKingApplication.class, args);
    }
    
    /**
     * 注意：@LoadBalanced注解，使用该注解，则调用其他服务时，必须使用服务名称[http://SERVICE-XXX]，而非IP:port
     * 使用场景: 都在网关内部, 走ribbon调用
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }
}
