package com.xyz.caofancpu.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * FileName: RestTemplateConfig
 * Author:   caofanCPU
 * Date:     2018/11/17 16:54
 */
@Configuration
public class RestTemplateConfig {
    
    @Resource
    RestTemplateBuilder restTemplateBuilder;
    
    /**
     * 注意：@LoadBalanced注解，使用该注解，则调用其他服务时，必须使用服务名称[http://SERVICE-XXX]，而非IP:port
     *
     * @return
     */
    @Bean(name = "restTemplate")
    @LoadBalanced
    RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }
    
    /**
     * 通过IP:port访问服务
     *
     * @return
     */
    @Bean(name = "zuulRestTemplate")
    RestTemplate zuulRestTemplate() {
        return restTemplateBuilder.build();
    }
    
    
}
