package com.xyz.caofancpu;

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

import static com.xyz.caofancpu.util.dataOperateUtils.JSONUtil.formatStandardJSON;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DebuggerKingApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class DebuggerKingApplicationTests {
    
    private MockMvc mvc;
    
    @Autowired
    private WebApplicationContext context;
    
    @Before
    public void setupMockMvc()
            throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Value("${test.service.http}")
    private String testAccessUrl;
    
    @Value("${sms_send_voice}")
    private Integer isSendVoice;
    
    /**
     * spring boot项目启动后，从数据库读取配置变量，将其放入容器中，业务代码可使用@Value注解取出使用
     */
    @Test
    public void initContextPropertyTest() {
        System.out.print("\n" + testAccessUrl + "\n" + isSendVoice);
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
    @Ignore
    public String handleParamExecute(MultiValueMap<String, String> paramMap, String url)
            throws Exception {
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
        params.entrySet().stream()
                .filter(item -> item.getValue() != null)
                .forEach(item -> convertResult.put(item.getKey(), new ArrayList() {
                            {
                                add(item.getValue().toString());
                            }
                        })
                );
        return convertResult;
    }
    
    /**
     * 控制台打印输出测试结果
     *
     * @param str
     */
    @Ignore
    public void output(String str) {
        System.out.println("测试结果 : \n" + formatStandardJSON(str));
    }
    
}

