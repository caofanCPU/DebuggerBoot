package com.xyz.caofancpu.controller.util;

import com.fasterxml.jackson.databind.ObjectMapper;
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

/**
 * Created by caofanCPU on 2018/7/25.
 * <p>
 * 用来创建测试环境；
 * 提供POST请求 @RequestBody 传对象
 * 提供POST请求 @RequestParam 传参数
 * 将接口结果JSON化打印
 */
@Component
public class TestUtil {
    
    private MockMvc mvc;
    
    @Autowired
    private WebApplicationContext context;
    
    public void setupMockMvc()
            throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    public String handleBodyExecute(Object parameter, String url)
            throws Exception {
        
        ObjectMapper mapper = new ObjectMapper();
        String responseString = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                /* 使用writeValueAsString() 方法来获取对象的JSON字符串表示 */
                .content(mapper.writeValueAsString(parameter)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        
        return responseString;
    }
    
    /**
     * 模拟发送RequestParam请求
     *
     * @param paramMap
     * @param url
     * @return
     * @throws Exception
     */
    public String handleParamExecute(MultiValueMap<String, String> paramMap, String url)
            throws Exception {
        String responseString = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                /* 使用writeValueAsString() 方法来获取对象的JSON字符串表示 */
                .params(paramMap))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        
        return responseString;
    }
    
    public MultiValueMap<String, String> convertRequestParam(Map<String, Object> params)
            throws Exception {
        if (params == null) {
            return null;
        }
        MultiValueMap<String, String> convertResult = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            convertResult.put(entry.getKey(), new ArrayList() {{
                add(entry.getValue().toString());
            }});
        }
        return convertResult;
    }
    
}
