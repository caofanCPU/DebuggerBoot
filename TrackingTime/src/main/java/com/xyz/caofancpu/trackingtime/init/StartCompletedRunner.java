package com.xyz.caofancpu.trackingtime.init;

import com.xyz.caofancpu.mvc.config.CommonConfigValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Created by caofanCPU on 2018/7/20.
 */
@Component
@DependsOn("commonConfigValueService")
@Order(value = 1)
@Slf4j
public class StartCompletedRunner implements CommandLineRunner {

    @Autowired
    private CommonConfigValueService commonConfigValueService;

    @Override
    public void run(String... strings) {
        String serverIp = null;
        try {
            serverIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            if (Objects.isNull(serverIp)) {
                serverIp = "UnknownHost";
            }
        }
        log.info("项目启动完成，请访问：http://" + serverIp + ":" + commonConfigValueService.serverPort + commonConfigValueService.contentPath + "/swagger-ui.html");
        log.info("SwaggerApi认证key：Authorization");
    }
}
