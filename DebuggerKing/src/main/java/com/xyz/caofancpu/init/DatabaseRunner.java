package com.xyz.caofancpu.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by caofanCPU on 2018/7/20.
 */
@Component
@Order(value = 1)
@Slf4j
public class DatabaseRunner implements CommandLineRunner {
    @Override
    public void run(String... strings) {
        log.info("项目启动完成，请访问：http://localhost:8098/debuggerking/swagger-ui.html");
        log.info("SwaggerApi认证key：Authorization");
    }
}
