package com.xyz.caofancpu.utils;

import com.xyz.caofancpu.util.commonOperateUtils.FileUtil;
import com.xyz.caofancpu.util.multiThreadUtils.BatchGroupRunnable;
import com.xyz.caofancpu.util.multiThreadUtils.DebuggerKingRunnable;
import com.xyz.caofancpu.util.multiThreadUtils.ThreadTraceUtil;
import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
public class BatchGroupRunnableTest {

    private static final String FILE_BASE_PATH = "/Users/htuser-085/Desktop/CAOFAN/IDEA-WORK/DebuggerBoot/DebuggerKingUtil/src/main/java/com/xyz/caofancpu/util/multiThreadUtils/textFile";

    public static void main(String[] args) {
        ThreadTraceUtil.beginTrace();
        ThreadPoolTaskExecutor pool = getStandardThreadPool();
        List<Integer> oddList = Arrays.asList(1, 3, 7);
        List<Integer> evenList = Arrays.asList(4, 8, 10);
        String oddFilePath = FILE_BASE_PATH + File.separator + "odd.txt";
        String evenFilePath = FILE_BASE_PATH + File.separator + "even.txt";
        BatchGroupRunnable batchGroupRunnable = new BatchGroupRunnable();
        batchGroupRunnable.onUpdateRate((total, currentValue) -> {
            if (total == 0) {
                System.out.println("XXXXXXXXXXXXXXXXXXX");
            }
            float rate = (currentValue + 0f) / total * 100;
            System.out.println("总体进度: " + rate);
        });
        batchGroupRunnable.onSuccess(() -> System.out.println("任务执行完成！"));
        createGroupTask(batchGroupRunnable, oddList, oddFilePath);
        createGroupTask(batchGroupRunnable, evenList, evenFilePath);
        batchGroupRunnable.execute(pool);
        pool.shutdown();
        ThreadTraceUtil.endTrace();
    }

    private static void createGroupTask(BatchGroupRunnable batchGroupRunnable, List<Integer> taskIdList, String filePath) {
        ConcurrentLinkedDeque<String> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
        BatchGroupRunnable currentBatchRunnable = batchGroupRunnable.createBatchRunnable();
        for (Integer taskId : taskIdList) {
            currentBatchRunnable.addRunnable(new DebuggerKingRunnable("执行任务[" + taskId + "]", concurrentLinkedDeque));
        }
        currentBatchRunnable.onSuccess(() -> {
            List<String> contentLines = new ArrayList<>(concurrentLinkedDeque);
            try {
                FileUtil.writeStringToFile(CollectionUtil.join(contentLines, "\n"), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static ThreadPoolTaskExecutor getStandardThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(16);
        executor.setThreadNamePrefix("debugger_");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }

}
