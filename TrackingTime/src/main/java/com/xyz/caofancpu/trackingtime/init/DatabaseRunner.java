package com.xyz.caofancpu.trackingtime.init;

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
        logger.info("项目启动完成，请访问：http://localhost:8098/debuggerking/swagger-ui.html");
        logger.info("SwaggerApi认证key：Authorization");
    }
}
