package com.xyz.caofancpu.springdata;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class SpringDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);
    }
    
}

