package com.ovfintech.bynz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BynzWebScApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class BynzWebScApplicationTests {
    
    private MockMvc mvc;
    
    @Autowired
    private WebApplicationContext context;
    
    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Value("${econtract.service.http}")
    private String eContractAccessUrl;
    
    @Value("${e_contract_sms_send_voice}")
    private Integer isSendVoice;
    
    /**
     * spring boot项目启动后，从数据库读取配置变量，将其放入容器中，业务代码可使用@Value注解取出使用
     */
    @Test
    public void initContextPropertyTest() {
        System.out.print("\n" + eContractAccessUrl + "\n" + isSendVoice);
    }
    
    
    /**
     * 模拟发送RequestBody对象的请求
     *
     * @param parameter
     * @param url
     * @return
     * @throws Exception
     */
    @Ignore
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
    @Ignore
    public String handleParamExecute(MultiValueMap<String, String> paramMap, String url) throws Exception {
        String responseString = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                /* 使用writeValueAsString() 方法来获取对象的JSON字符串表示 */
                .params(paramMap))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        
        return responseString;
    }
    
    @Ignore
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
    @Ignore
    public void output(String str) {
        System.out.println("测试结果 : \n" + outputJson(str));
    }
    
    /**
     * 将Json字符串格式化，用于控制台输出
     *
     * @param jsonStr
     * @return
     */
    @Ignore
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
        return jsonResultStr.toString();
    }
    
    
}
