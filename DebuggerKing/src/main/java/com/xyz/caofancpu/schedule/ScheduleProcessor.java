package com.xyz.caofancpu.schedule;

import com.xyz.caofancpu.core.DateUtil;
import com.xyz.caofancpu.extra.NormalUseForTestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by caofanCPU on 2018/7/20.
 */
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleProcessor {

    private static int count = 1;

    /**
     * 1分钟跑一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void doSchedule() {
        log.info("\n"
                + DateUtil.getCurrentDateTime(DateUtil.DATETIME_FORMAT_SIMPLE)
                + ".007  帝八哥 2018 --- [           Debugger] K.I.N.G :"
                + " Demo定时任务第[" + count + "]次执行...");
        count++;
    }

    /**
     * 常用Cron表达式
     */
    private void outNormalCronExpression() {
        NormalUseForTestUtil.out("Cron表达式范例：\n" +
                "             每隔5秒执行一次:                  */5 * * * * ?\n" +
                "             每隔5分钟执行一次:                0 */5 * * * ?\n" +
                "             每天23点执行一次:                 0 0 23 * * ?\n" +
                "             每天凌晨2点执行一次:               0 0 2 * * ?\n" +
                "             每月2号凌晨4点执行一次：            0 0 2 4 * ?\n" +
                "             每月最后一天23点执行一次：          0 0 23 L * ?\n" +
                "             每周星期天凌晨2点实行一次：          0 0 2 ? * L\n" +
                "             在26分、29分、33分执行一次：        0 26,29,33 * * * ?\n" +
                "             每天的0点、22点、23点都执行一次：    0 0 0 0,22,23 * * ?\n"
        );
    }

}
