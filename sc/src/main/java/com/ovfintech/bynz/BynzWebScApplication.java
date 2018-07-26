package com.ovfintech.bynz;

/**
 * Created by caofanCPU on 2018/7/24.
 */

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ovfintech.bynz.mapper")
@EnableEncryptableProperties
public class BynzWebScApplication {
    
    @Resource
    RestTemplateBuilder restTemplateBuilder;
    
    public static void main(String[] args) {
        SpringApplication.run(BynzWebScApplication.class, args);
    }
    
    /**
     * 注意：@LoadBalanced注解，使用该注解，则调用其他服务时，必须使用服务名称[http://SERVICE-XXX]，而非IP:port
     *
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }
}
