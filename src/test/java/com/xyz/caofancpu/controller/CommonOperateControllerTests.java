package com.xyz.caofancpu.controller;

import com.xyz.caofancpu.DebuggerKingApplication;
import com.xyz.caofancpu.controller.util.TestUtil;
import com.xyz.caofancpu.utils.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by caofanCPU on 2018/7/25.
 * <p>
 * 分页测试代码，示例
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DebuggerKingApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class CommonOperateControllerTests {
    
    @Resource(type = TestUtil.class)
    private TestUtil testUtil;
    
    /**
     * 初始化测试环境context
     *
     * @throws Exception
     */
    @Before
    public void setupMockMvc()
            throws Exception {
        testUtil.setupMockMvc();
    }
    
    @Test
    public void listSysDictByPageTest()
            throws Exception {
        HashMap<String, Object> params = new HashMap();
        params.put("demo", "this is demo");
        MultiValueMap<String, String> requestParams = testUtil.convertRequestParam(params);
        String responseStr = testUtil.handleParamExecute(requestParams, "/sysDict/listByPage");
        JSONUtil.output(responseStr);
    }
    
}
