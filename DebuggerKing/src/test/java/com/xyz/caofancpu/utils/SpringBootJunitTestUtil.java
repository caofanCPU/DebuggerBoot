package com.xyz.caofancpu.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Created by caofanCPU on 2018/7/25.
 * <p>
 * 用来创建测试环境；
 * 提供POST请求 @RequestBody 传对象
 * 提供POST请求 @RequestParam 传参数
 * 将接口结果JSON化打印
 */
@Component
public class SpringBootJunitTestUtil {
    
    private MockMvc mvc;
    
    @Autowired
    private WebApplicationContext context;
    
    public void setupMockMvc()
            throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    /**
     * 模拟发送RequestBody请求
     * POST
     *
     * @param bodyObject
     * @param url
     * @return
     * @throws Exception
     */
    public String handlePostBodyExecute(Object bodyObject, String url)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String responseString = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                /* 使用writeValueAsString() 方法来获取对象的JSON字符串表示 */
                .content(mapper.writeValueAsString(bodyObject)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        return responseString;
    }
    
    /**
     * 模拟发送RequestParam请求
     * POST
     *
     * @param paramMap
     * @param url
     * @return
     * @throws Exception
     */
    public String handlePostParamExecute(MultiValueMap<String, String> paramMap, String url)
            throws Exception {
        String responseString = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                /* 使用writeValueAsString() 方法来获取对象的JSON字符串表示 */
                .params(paramMap))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        return responseString;
    }
    
    /**
     * 模拟发送RequestParam请求
     * GET
     *
     * @param paramMap
     * @param url
     * @return
     * @throws Exception
     */
    public String handleGetParamExecute(MultiValueMap<String, String> paramMap, String url)
            throws Exception {
        String responseString = mvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                /* 使用writeValueAsString() 方法来获取对象的JSON字符串表示 */
                .params(paramMap))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        return responseString;
    }
    
    /**
     * SpringBoot模拟Rest请求时,传参数时需接收 MultiValueMap
     * 该方法只做转换, 将Map转为MultiValueMap
     *
     * @param params
     * @return
     * @throws Exception
     */
    public MultiValueMap<String, String> convertRequestParam(Map<String, Object> params)
            throws Exception {
        if (Objects.isNull(params)) {
            return null;
        }
        MultiValueMap<String, String> convertResult = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (Objects.isNull(entry.getValue())) {
                continue;
            }
            convertResult.put(entry.getKey(), new ArrayList() {{
                add(entry.getValue().toString());
            }});
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
    
}
