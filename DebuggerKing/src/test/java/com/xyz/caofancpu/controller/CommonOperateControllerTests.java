package com.xyz.caofancpu.controller;

import com.xyz.caofancpu.DebuggerKingApplicationTests;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caofanCPU on 2018/7/25.
 */
public class CommonOperateControllerTests extends DebuggerKingApplicationTests {
    
    private static final Logger logger = LoggerFactory.getLogger(CommonOperateControllerTests.class);
    
    @Test
    public void listSysDictByPageTest()
            throws Exception {
        Map<String, Object> params = new HashMap<String, Object>(2, 0.5f) {
            {
                put("demo", "this is demo");
            }
        };
        MultiValueMap<String, String> requestParams = springBootJunitTestUtil.convertRequestParam(params);
        String responseStr = springBootJunitTestUtil.handlePostParamExecute(requestParams, "/sysDict/listByPage");
        JSONUtil.formatStandardJSON(responseStr);
    }
    
    @Test
    public void testRuntimeException()
            throws Exception {
        String responseStr = springBootJunitTestUtil.handlePostBodyExecute(new HashMap<>(2, 0.5f), "/testRuntimeException");
        JSONUtil.formatStandardJSON(responseStr);
    }
}
