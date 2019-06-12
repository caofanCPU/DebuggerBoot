package com.xyz.caofancpu.schedule;

import com.xyz.caofancpu.util.dataOperateUtils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Created by caofanCPU on 2018/7/20.
 */
@Configuration
@EnableScheduling
public class ScheduleProcessor {
    
    private static int count = 1;
    private final Logger logger = LoggerFactory.getLogger(ScheduleProcessor.class);
    
    /**
     * 1分钟跑一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void doSchedule() {
        logger.info("\n"
                + DateUtil.date2Str(new Date(), DateUtil.FORMAT_SIMPLE_DETAIL)
                + ".007  帝八哥 2018 --- [           Debugger] K.I.N.G :"
                + " Demo定时任务第[" + count + "]次执行...");
        count++;
    }
}
