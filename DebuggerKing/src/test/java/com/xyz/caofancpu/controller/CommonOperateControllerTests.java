package com.xyz.caofancpu.controller;

import com.google.common.collect.Maps;
import com.xyz.caofancpu.DebuggerKingApplicationTests;
import com.xyz.caofancpu.constant.HttpTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.Rollback;

import javax.servlet.http.Cookie;
import java.util.Map;

/**
 * Created by caofanCPU on 2018/7/25.
 */
@Slf4j
public class CommonOperateControllerTests extends DebuggerKingApplicationTests {

    @Test
    public void listSysDictByPageTest()
            throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("demo", "this is a demo");

        HttpHeaders httpHeaders = springBootJunitTestUtil.generateRequestHeaders();
        Cookie[] cookies = springBootJunitTestUtil.buildMockHttpServletRequestCookie();
        springBootJunitTestUtil.execute(params, "/sysDict/listByPage", httpHeaders, HttpTypeEnum.POST_PARAM, cookies);
    }

    @Test
    @Rollback(false)
    public void batchInsertWithIdsBackTest()
            throws Exception {
        HttpHeaders httpHeaders = springBootJunitTestUtil.generateRequestHeaders();
        Cookie[] cookies = springBootJunitTestUtil.buildMockHttpServletRequestCookie();
        springBootJunitTestUtil.execute(null, "/sysDict/testBatchInsertWithBackIds", httpHeaders, HttpTypeEnum.POST_BODY, cookies);
    }

}
