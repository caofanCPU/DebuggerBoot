package com.xyz.caofancpu.util.StudyWaitingUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * FileName: MultiThreadPlayUtil
 *
 * @author: caofanCPU
 * @date: 2019/3/1 15:28
 */

public class MultiThreadPlayUtil {
    
    public static ExecutorService executorService;
    
    public static int commonLimitThreadThreshold = 4;
    
    public static void out(String text) {
        System.out.println(text);
    }
    
    public static MultiThreadPlayUtil init() {
        MultiThreadPlayUtil multiThreadPlayUtil = new MultiThreadPlayUtil();
        multiThreadPlayUtil.initThreadPool();
        return multiThreadPlayUtil;
    }
    
    public static void main(String[] args) {
        MultiThreadPlayUtil multiThreadPlayUtil = init();
        multiThreadPlayUtil.testSemaphoreDemo();
        // 此处关闭线程池, 使用的是shutdown()方法, 该方法会等到线程池任务执行完毕后才关闭线程池
        multiThreadPlayUtil.shutdownThreadPool();
    }
    
    public void testSemaphoreDemo() {
        Semaphore monitorSemaphore = getMonitorSemaphore(3);
        for (int i = 1; i <= 10; i++) {
            executorService.execute(new SemaphoreDemoRunnable(monitorSemaphore, i));
        }
    }
    
    public Semaphore getMonitorSemaphore(int limitThreadNum) {
        return new Semaphore(limitThreadNum > 0 ? limitThreadNum : commonLimitThreadThreshold);
    }
    
    public void initThreadPool() {
        executorService = Executors.newCachedThreadPool();
    }
    
    public void shutdownThreadPool() {
        executorService.shutdown();
    }
    
    class SemaphoreDemoRunnable implements Runnable {
        private Semaphore semaphore;
        private int threadNo;
        
        public SemaphoreDemoRunnable(Semaphore semaphore, int threadNo) {
            this.semaphore = semaphore;
            this.threadNo = threadNo;
        }
        
        @Override
        public void run() {
            try {
                semaphore.acquire();
                out("线程标识号码[" + this.threadNo + "]进入红灯区, 体验服务...");
                Thread.currentThread().sleep(10000L);
                out("线程标识号码[" + this.threadNo + "]舒服完毕, 闲庭信步走出红灯区...");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
