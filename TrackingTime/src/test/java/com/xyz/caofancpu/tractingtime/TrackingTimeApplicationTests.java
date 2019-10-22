package com.xyz.caofancpu.tractingtime;

import com.xyz.caofancpu.mvc.test.SpringBootJunitTestUtil;
import com.xyz.caofancpu.trackingtime.TrackingTimeApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrackingTimeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class TrackingTimeApplicationTests {

    @Resource(type = SpringBootJunitTestUtil.class)
    public SpringBootJunitTestUtil springBootJunitTestUtil;

}
