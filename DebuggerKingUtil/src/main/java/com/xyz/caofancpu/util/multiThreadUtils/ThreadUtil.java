package com.xyz.caofancpu.util.multiThreadUtils;

import com.xyz.caofancpu.util.logger.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程相关
 */
public class ThreadUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadUtil.class);

    public static void run(String threadTraceId, Runnable runnable, OnSuccessCallback success) {
        try {
            ThreadTraceUtil.beginTrace();
            LoggerUtil.info(LOG, "线程执行开始", "父线程traceId", threadTraceId);
            runnable.run();
            LoggerUtil.info(LOG, "线程执行结束", "父线程traceId", threadTraceId);
        } catch (Throwable e) {
            LoggerUtil.error(LOG, "线程执行异常", e, "traceId", threadTraceId);
        } finally {
            if (success != null) {
                try {
                    success.callback();
                } catch (Exception e) {
                    LoggerUtil.error(LOG, "线程结束.回调异常", e, "traceId", threadTraceId);
                }
            }
            ThreadTraceUtil.endTrace();
        }
    }
}
