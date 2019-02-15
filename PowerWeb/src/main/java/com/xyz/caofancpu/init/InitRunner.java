package com.xyz.caofancpu.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 读取自定义配置变量, 设置为全局
 *
 * @author caofanCPU
 * @date 2018-08-03
 */
@Component
@Order(value = 2)
public class InitRunner implements CommandLineRunner {
    
    /**
     * LOGGER
     */
    private final Logger logger = LoggerFactory.getLogger(InitRunner.class);
    
    @Override
    public void run(String... args)
            throws Exception {
        logger.info("帝八哥 2018 --- [           Debugger] K.I.N.G : 项目启动成功!");
    }
}
