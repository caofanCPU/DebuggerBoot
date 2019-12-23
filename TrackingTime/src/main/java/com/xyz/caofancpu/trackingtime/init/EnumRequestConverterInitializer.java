package com.xyz.caofancpu.trackingtime.init;

import com.xyz.caofancpu.util.commonoperateutils.enumtype.EnumRequestJSONConverterUtil;
import com.xyz.caofancpu.util.dataoperateutils.MappingJackson2HttpMessageConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.convert.ApplicationConversionService;

/**
 * 字符串转枚举
 * 请求枚举转换: value -> name -> viewName
 * <p>
 * 数值转枚举
 * 请求枚举转换: value -> name -> viewName
 *
 * @deprecated 原因: SpringBoot默认配置会覆盖webConfigure中的配置
 * 且请求过来首先要经过HttpMessageConvert转换
 * 因而使用{@link MappingJackson2HttpMessageConverterUtil#build()}方式
 */

@Slf4j
@Deprecated
public class EnumRequestConverterInitializer {

    public void run(String... args)
            throws Exception {
        ApplicationConversionService applicationConversionService = (ApplicationConversionService) ApplicationConversionService.getSharedInstance();
        applicationConversionService.removeConvertible(String.class, Enum.class);
        applicationConversionService.removeConvertible(Integer.class, Enum.class);
        applicationConversionService.addConverterFactory(EnumRequestJSONConverterUtil.buildStringToEnumConverterFactory());
        applicationConversionService.addConverterFactory(EnumRequestJSONConverterUtil.buildValueToEnumConverterFactory());
        log.info("DebuggerKing....枚举请求转换器初始化完成!");
    }
}
