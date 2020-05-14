package com.xyz.caofancpu;

import com.xyz.caofancpu.test.SpringBootJunitTestUtil;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
 * 该配置用于解决 webSocket启动问题
 * 测试用例强制使用开发环境
 * DebuggerKingApplicationTests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DebuggerKingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class DebuggerKingApplicationTests {

    @Resource(type = SpringBootJunitTestUtil.class)
    public SpringBootJunitTestUtil springBootJunitTestUtil;

}

