package com.xyz.caofancpu.tractingtime;

import com.google.common.collect.Maps;
import com.xyz.caofancpu.constant.HttpTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import java.util.Map;

/**
 * Created by caofanCPU on 2018/7/25.
 */
@Slf4j
public class CommonOperateControllerTests extends TrackingTimeApplicationTests {

    @Test
    public void npeTest()
            throws Exception {
        HttpHeaders httpHeaders = springBootJunitTestUtil.generateRequestHeaders();
        Cookie[] cookies = springBootJunitTestUtil.buildMockHttpServletRequestCookie();
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("id", "1000L");
        springBootJunitTestUtil.execute(paramMap, "/testException", httpHeaders, HttpTypeEnum.POST_BODY, cookies);
    }

    @Test
    public void listSysDictByPageTest()
            throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("demo", "this is a demo");

        HttpHeaders httpHeaders = springBootJunitTestUtil.generateRequestHeaders();
        Cookie[] cookies = springBootJunitTestUtil.buildMockHttpServletRequestCookie();
        springBootJunitTestUtil.execute(params, "/sysDict/listByPage", httpHeaders, HttpTypeEnum.POST_PARAM, cookies);
    }
}
