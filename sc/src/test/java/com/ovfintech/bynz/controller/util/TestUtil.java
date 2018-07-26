package com.ovfintech.bynz.controller.util;

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
    
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    public String handleBodyExecute(Object parameter, String url) throws Exception {
        
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
    public String handleParamExecute(MultiValueMap<String, String> paramMap, String url) throws Exception {
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
    
    /**
     * 控制台打印输出测试结果
     *
     * @param str
     */
    public void output(String str) {
        System.out.println("测试结果 : \n" + outputJson(str));
    }
    
    /**
     * 将Json字符串格式化，用于控制台输出
     *
     * @param jsonStr
     * @return
     */
    public String outputJson(String jsonStr) {
        String start = "    ";
        // 用户标记层级
        int level = 0;
        StringBuilder jsonResultStr = new StringBuilder();
        // 循环遍历每一个字符
        for (int i = 0; i < jsonStr.length(); i++) {
            // 获取当前字符
            char piece = jsonStr.charAt(i);
            // 如果上一个字符是断行，则在本行开始按照level数值添加标记符，排除第一行
            if (i != 0 && '\n' == jsonResultStr.charAt(jsonResultStr.length() - 1)) {
                for (int k = 0; k < level; k++) {
                    jsonResultStr.append(start);
                }
            }
            switch (piece) {
                case '{':
                case '[':
                    // 如果字符是{或者[，则断行，level加1
                    jsonResultStr.append(piece + "\n");
                    level++;
                    break;
                case ',':
                    // 如果是","，则断行
                    jsonResultStr.append(piece + "\n");
                    break;
                case '}':
                case ']':
                    // 如果是"}"或者"]"，则断行，level减1
                    jsonResultStr.append("\n");
                    level--;
                    for (int k = 0; k < level; k++) {
                        jsonResultStr.append(start);
                    }
                    jsonResultStr.append(piece);
                    break;
                default:
                    jsonResultStr.append(piece);
                    break;
            }
        }
        return jsonResultStr.toString().replaceAll("\n\n", "\n");
    }
    
}
