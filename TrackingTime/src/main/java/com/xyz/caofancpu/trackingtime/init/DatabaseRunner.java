package com.xyz.caofancpu.trackingtime.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Created by caofanCPU on 2018/7/20.
 */
@Component
@Order(value = 1)
@Slf4j
public class DatabaseRunner implements CommandLineRunner, ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;
    private String serverIp;

    @Override
    public void run(String... strings) {
        log.info("项目启动完成，请访问：http://localhost:8098/debuggerking/swagger-ui.html");
        log.info("SwaggerApi认证key：Authorization");
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
        try {
            this.serverIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            if (Objects.isNull(this.serverIp)) {
                this.serverIp = "UnknownHost";
            }
        }
    }
}
