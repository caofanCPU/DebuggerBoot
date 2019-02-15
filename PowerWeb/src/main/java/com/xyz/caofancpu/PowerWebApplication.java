package com.xyz.caofancpu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.xyz.caofancpu"})
public class PowerWebApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PowerWebApplication.class, args);
    }
    
}

