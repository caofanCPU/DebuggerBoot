package com.xyz.caofancpu.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.IEnum;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.Objects;

/**
 * Created by caofanCPU on 2018/7/25.
 * <p>
 * 用来创建测试环境
 * 提供POST请求 @RequestBody 传对象
 * 提供POST请求 @RequestParam 传参数
 * 提供 Get请求 @RequestParam 传参数
 * 将接口结果JSON化打印
 */
@Component
public class SpringBootJunitTestUtil {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    /**
     * 默认就执行
     * 设置context
     */
    @PostConstruct
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * @param requestData  请求数据: @RequestParam || @RequestBody
     * @param url          接口API地址(不要域名和端口的URI信息)
     * @param httpHeaders  请求头
     * @param httpTypeEnum 请求类型
     * @param cookies      请求携带的cookies
     * @return
     * @throws Exception
     */
    public String execute(Object requestData, String url, HttpHeaders httpHeaders, HttpTypeEnum httpTypeEnum, Cookie... cookies)
            throws Exception {
        String responseJsonString = this.doHttp(requestData, url, httpHeaders, httpTypeEnum, cookies);
        output(responseJsonString);
        return responseJsonString;
    }

    /**
     * @param requestData  请求数据: @RequestParam || @RequestBody
     * @param url          接口API地址(不要域名和端口的URI信息)
     * @param httpHeaders  请求头
     * @param httpTypeEnum 请求类型
     * @param cookies      请求携带的cookies
     * @return
     * @throws Exception
     */
    public String doHttp(Object requestData, String url, HttpHeaders httpHeaders, HttpTypeEnum httpTypeEnum, Cookie... cookies)
            throws Exception {
        return mvc.perform(loadMockHttpServletRequestBuilder(requestData, url, httpHeaders, httpTypeEnum, cookies))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private MockHttpServletRequestBuilder loadMockHttpServletRequestBuilder(Object requestData, String url, HttpHeaders httpHeaders, HttpTypeEnum httpTypeEnum, Cookie... cookies)
            throws JsonProcessingException {
        switch (httpTypeEnum) {
            case POST_BODY:
                // 模拟POST发送RequestBody请求
                return MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .headers(httpHeaders)
                        .cookie(cookies)
                        .content(new ObjectMapper().writeValueAsString(requestData));
            case POST_PARAM:
                // 模拟POST发送RequestParam请求
                MultiValueMap<String, String> multiValueMap = convertRequestParam(JSONObject.parseObject(JSONObject.toJSONString(requestData)));
                return MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .headers(httpHeaders)
                        .cookie(cookies)
                        .params(multiValueMap);
            case GET_PARAM:
                // 模拟GET发送RequestParam请求
                multiValueMap = convertRequestParam(JSONObject.parseObject(JSONObject.toJSONString(requestData)));
                return MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .headers(httpHeaders)
                        .cookie(cookies)
                        .params(multiValueMap);
            default:
                throw new RuntimeException("暂不支持Http请求方式: " + httpTypeEnum.getName());
        }
    }

    public HttpHeaders generateRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        return headers;
    }

    /**
     * 构建MockHttpServletRequest类型的cookie
     *
     * @return
     */
    public Cookie[] buildMockHttpServletRequestCookie() {
        return Lists.newArrayList(new Cookie("Cookie", "empty")).toArray(new Cookie[0]);
    }


    /**
     * SpringBoot模拟Rest请求时,传参数时需接收 MultiValueMap
     * 该方法只做转换, 将Map转为MultiValueMap
     *
     * @param paramMap
     * @return
     */
    private MultiValueMap<String, String> convertRequestParam(@NonNull Map<String, Object> paramMap) {
        MultiValueMap<String, String> convertResult = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (Objects.isNull(entry.getValue())) {
                continue;
            }
            convertResult.put(entry.getKey(), Lists.newArrayList(entry.getValue().toString()));
        }
        return convertResult;
    }

    /**
     * 控制台打印输出测试结果
     *
     * @param str
     */
    public void output(String str) {
        System.out.println("[测试结果]: \n" + JSONUtil.formatStandardJSON(str));
    }

    public enum HttpTypeEnum implements IEnum {
        POST_BODY(0, "Post传对象"),
        POST_PARAM(1, "Post传参数"),
        GET_PARAM(2, "Get传参数"),

        ;

        private int value;

        private String name;

        HttpTypeEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        @Override
        public Integer getValue() {
            return this.value;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

}
