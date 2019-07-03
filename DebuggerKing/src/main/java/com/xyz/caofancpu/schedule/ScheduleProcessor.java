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

    /**
     * 常用Cron表达式
     */
    private void outNormalCronExpression() {
        System.out.println("Cron表达式范例：\n" +
                "             每隔5秒执行一次：*/5 * * * * ?\n" +
                "             每隔1分钟执行一次：0 */1 * * * ?\n" +
                "             每天23点执行一次：0 0 23 * * ?\n" +
                "             每天凌晨1点执行一次：0 0 1 * * ?\n" +
                "             每月1号凌晨1点执行一次：0 0 1 1 * ?\n" +
                "             每月最后一天23点执行一次：0 0 23 L * ?\n" +
                "             每周星期天凌晨1点实行一次：0 0 1 ? * L\n" +
                "             在26分、29分、33分执行一次：0 26,29,33 * * * ?\n" +
                "             每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?\n"
        );
    }

}
