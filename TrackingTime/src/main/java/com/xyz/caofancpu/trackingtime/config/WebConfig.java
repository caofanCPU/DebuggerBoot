package com.xyz.caofancpu.trackingtime.config;

import com.xyz.caofancpu.util.dataOperateUtils.MappingJackson2HttpMessageConverterUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WEB配置
 * 可增加拦截器、异步/跨域支持
 * <p>
 * 注意： 1.@EnableWebMvc + implements WebMvcConfigurer
 * 2.extends WebMvcConfigurationSupport
 * 都会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
 * 例如: 请求参数时间格式/响应字段为NULL剔除
 * 因此, 推荐使用 implements WebMvcConfigurer方式, 保留原有配置
 * 3.自定义消息转换器, 请使用extendMessageConverters,
 * 不要使用configureMessageConverters, 因为该方法要么不起作用, 要么关闭了默认配置
 *
 * @author caofan
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 剔除响应对象中为NULL的字段
     * 枚举类转换: viewName > name
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter customerMappingJackson2HttpMessageConverter = MappingJackson2HttpMessageConverterUtil.build();
        // 原有默认转换器
        HttpMessageConverter defaultMappingJackson2HttpMessageConverter = null;
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                defaultMappingJackson2HttpMessageConverter = converters.get(i);
                // 前置自定义转换器
                converters.set(i, customerMappingJackson2HttpMessageConverter);
                break;
            }
        }
        // 保存原有默认转换器(备用)
        converters.add(defaultMappingJackson2HttpMessageConverter);
    }

}
