package com.ovfintech.bynz.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by caofanCPU on 2018/7/20.
 */
@Component
@Order(value = 1)
public class InitStartupRunner implements CommandLineRunner {
    
    private final Logger LOGGER = LoggerFactory.getLogger(InitStartupRunner.class);
    
    @Autowired
    private InitContextProperty initContextProperty;
    
    @Override
    public void run(String... strings) throws Exception {
        LOGGER.info("帝八哥已就绪[Debugger].K.I.N.G");
        initContextProperty.excute();
    }
}
