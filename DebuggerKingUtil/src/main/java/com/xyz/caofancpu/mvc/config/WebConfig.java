package com.xyz.caofancpu.mvc.config;

import com.xyz.caofancpu.util.dataOperateUtils.MappingJackson2HttpMessageConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WEB配置
 * 可增加拦截器、异步/跨域支持
 * <p>
 * 注意： 1.@EnableWebMvc + implements WebMvcConfigurer
 * 2.extends WebMvcConfigurationSupport
 * 都会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
 * 例如: 请求参数时间格式/响应字段为NULL剔除
 * 因此, 推荐使用 implements WebMvcConfigurer方式, 保留原有配置
 * 3.自定义消息转换器, 推荐直接使用注册Bean
 * 也可使用复写extendMessageConverters()方法,
 * 但是注意: 不要使用configureMessageConverters, 该方法要么不起作用, 要么关闭了默认配置
 *
 * @author caofan
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    /**
     * 剔除响应对象中为NULL的字段
     * 请求枚举类转换: value -> name -> viewName
     * 响应枚举类转换: viewName -> name
     *
     */
    @Bean(name = "customerMappingJackson2HttpMessageConverter")
    public HttpMessageConverter customerMappingJackson2HttpMessageConverter() {
        log.info("DebuggerKing....枚举请求&&响应转换器初始化完成!");
        return MappingJackson2HttpMessageConverterUtil.build();
    }
}
