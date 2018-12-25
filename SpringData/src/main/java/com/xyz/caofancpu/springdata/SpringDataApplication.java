package com.xyz.caofancpu.springdata;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author caofanCPU
 */
@SpringBootApplication(scanBasePackages = {"com.xyz.caofancpu.springdata"})
@MapperScan("com.xyz..*.mapper")
@EnableEncryptableProperties
public class SpringDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);
    }
    
}

