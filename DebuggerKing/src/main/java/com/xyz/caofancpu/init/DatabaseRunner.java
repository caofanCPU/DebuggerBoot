package com.xyz.caofancpu.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by caofanCPU on 2018/7/20.
 */
@Component
@Order(value = 1)
public class DatabaseRunner implements CommandLineRunner {
    
    private final Logger logger = LoggerFactory.getLogger(DatabaseRunner.class);
    
    @Override
    public void run(String... strings) {
        logger.info("帝八哥已就绪[Debugger].K.I.N.G");
    }
}
