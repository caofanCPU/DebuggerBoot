package com.xyz.caofancpu.trackingtime.service.configValue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * FileName: CommonConfigService
 * Author:   caofanCPU
 * Date:     2018/11/24 10:01
 * 该类用于统一封装其他serviceIml所需要的注入变量或 公用配置
 */
@Service("commonConfigValueService")
@DependsOn("initContextPropertyInitializer")
public class CommonConfigValueService {

    @Value("${app.name}")
    public String appName;

    @Value("${ms.file.url}")
    public String fileAccessUrl;

    @Value("${swagger.authorizationKey}")
    public String authKey;

    @Value("${fileOperate.logging.key}")
    public String fileOperateLoggingKey;

    @Value("${fileOperate.logging.value}")
    public String fileOperateLoggingValue;

    @Value("${task.handle.average}")
    public int taskHandleAverage;

    @Value("${task.rootPath}")
    public String taskRootPath;

    @Value("${task.resultPath}")
    public String taskResultPath;

}
