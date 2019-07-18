package com.xyz.caofancpu.util.StudyWaitingUtils;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;

/**
 * FileName: MultiThreadPlayUtil
 */

public class MultiThreadPlayUtil {

    // ====================静态变量========================
    public static ExecutorService executorService;

    public static int commonLimitThreadThreshold = 4;

    public static long randomSeed = System.currentTimeMillis();

    // ====================静态方法=========================
    public static void out(String text) {
        System.out.println(text);
    }

    public static void initThreadPool() {
        executorService = Executors.newCachedThreadPool();
    }

    public static void shutdownThreadPool() {
        executorService.shutdown();
    }

    public static long getRandomWaitTime(int rate) {
        if (rate < 1) {
            rate = 1;
        }
        Random random = new Random(randomSeed);
        return (random.nextInt(9) + 2) * 1000 * rate;
    }

    // ======================================================

    public static void main(String[] args) {
        // must do: 初始化
        initThreadPool();

        // you can replace
        // test Semaphore
//        testSemaphoreDemo();

        // test CyclicBarrier
//        testCyclicBarrier();

        // test Exchanger
//        testExchanger();

        // test CountDownLatch
        testCountDownLatch();
        // must do: 收尾
        // 此处关闭线程池, 使用的是shutdown()方法, 该方法会等到线程池任务执行完毕后才关闭线程池
        shutdownThreadPool();
    }

    // ==================测试方法=========================
    public static void testSemaphoreDemo() {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        semaphoreDemo.execute();
    }

    public static void testCyclicBarrier() {
        CycleBarrierDemo cycleBarrierDemo = new CycleBarrierDemo();
        cycleBarrierDemo.execute();
    }

    public static void testExchanger() {
        ExchangerDemo exchangerDemo = new ExchangerDemo();
        exchangerDemo.execute();
    }

    public static void testCountDownLatch() {
        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();
        countDownLatchDemo.execute();
    }

    // ==================测试DEMO类=============================
    static class SemaphoreDemo {
        public void execute() {
            final Semaphore monitorSemaphore = getSemaphoreMonitor(3);
            for (int i = 1; i <= 10; i++) {
                executorService.execute(new SemaphoreRunnable(monitorSemaphore, i));
            }
        }

        public Semaphore getSemaphoreMonitor(int limitThreadNum) {
            return new Semaphore(limitThreadNum > 0 ? limitThreadNum : commonLimitThreadThreshold);
        }

        class SemaphoreRunnable implements Runnable {
            private Semaphore semaphore;
            private int threadNo;

            public SemaphoreRunnable(Semaphore semaphore, int threadNo) {
                this.semaphore = semaphore;
                this.threadNo = threadNo;
            }

            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    long waitTime = getRandomWaitTime(1);
                    out("线程标识号码[" + this.threadNo + "]开始, 执行[" + waitTime + "ms]时间...");
                    Thread.sleep(waitTime);
                    out("线程标识号码[" + this.threadNo + "]结束...");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class CycleBarrierDemo {
        public void execute() {
            final CyclicBarrier cyclicBarrier = getDefaultTaskedCycleBarrierMonitor();
            int limitThreadNum = commonLimitThreadThreshold >> 1;
            for (int i = 1; i <= limitThreadNum; i++) {
                if (i != 1) {
                    try {
                        Thread.sleep(getRandomWaitTime(1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                executorService.execute(new CycleBarrierRunnable(cyclicBarrier, i));
            }
        }

        public CyclicBarrier getDefaultCycleBarrierMonitor() {
            return new CyclicBarrier(commonLimitThreadThreshold >> 1);
        }

        public CyclicBarrier getCustomerCycleBarrierMonitor(int limitThreadNum) {
            return new CyclicBarrier(limitThreadNum > 0 ? limitThreadNum : commonLimitThreadThreshold);
        }

        public CyclicBarrier getDefaultTaskedCycleBarrierMonitor() {
            return new CyclicBarrier(commonLimitThreadThreshold >> 1,
                    () -> {
                        long waitTime = getRandomWaitTime(1);
                        out("---------------------------------------------------");
                        out("-----------------" + waitTime + "]ms---------------");
                        out("---------------------------------------------------");
                    }
            );
        }

        public CyclicBarrier getCustomerTaskedCycleBarrierMonitor(int limitThreadNum) {
            return new CyclicBarrier(limitThreadNum > 0 ? limitThreadNum : commonLimitThreadThreshold,
                    () -> {
                        // doSomething
                    }
            );
        }

        class CycleBarrierRunnable implements Runnable {
            private CyclicBarrier cyclicBarrier;
            private int threadNo;

            public CycleBarrierRunnable(CyclicBarrier cyclicBarrier, int threadNo) {
                this.cyclicBarrier = cyclicBarrier;
                this.threadNo = threadNo;
            }

            @Override
            public void run() {
                try {
                    Thread.sleep(getRandomWaitTime(1));
                    if (threadNo == 1) {
                        out("线程编号[" + threadNo + "]已就绪, [" + (cyclicBarrier.getNumberWaiting() + 1) + "]个线程正在等待...");
                    } else {
                        out("线程编号[" + threadNo + "]已就绪, [" + (cyclicBarrier.getNumberWaiting() + 1) + "]个线程正在等待...");
                    }
                    cyclicBarrier.await();
                    if (threadNo == 2) {
                        out("开始...");
                        Thread.sleep(getRandomWaitTime(2));
                        out("结束.....");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static class ExchangerDemo<V> {
        public Exchanger<V> getExchanger() {
            return new Exchanger<>();
        }

        public int getExchangeThreadNum(int limitThreadNum) {
            if (limitThreadNum < 2) {
                return commonLimitThreadThreshold << 1;
            }
            // 来一波骚操作
            return limitThreadNum >> 1 << 1;
        }

        public void execute() {
            final Exchanger<V> exchanger = getExchanger();
            int limitThreadNum = getExchangeThreadNum(7);
            V data;
            for (int i = 1; i <= limitThreadNum; i++) {
                if (i % 2 == 0) {
                    try {
                        Thread.sleep(getRandomWaitTime(1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    data = (V) ("偶数" + i);
                } else {
                    data = (V) ("奇数" + i);
                }
                executorService.execute(new ExchangerRunnable<>(exchanger, i, data));
            }
        }

        class ExchangerRunnable<V> implements Runnable {
            private Exchanger<V> exchanger;
            private int threadNo;
            private V data;

            public ExchangerRunnable(Exchanger<V> exchanger, int threadNo, V data) {
                this.exchanger = exchanger;
                this.threadNo = threadNo;
                this.data = data;
            }

            @Override
            public void run() {
                try {
                    Thread.sleep(getRandomWaitTime(1));
                    String originData = Objects.isNull(this.data) ? "空数据" : this.data.toString();
                    V result = exchanger.exchange(this.data);
                    String resultData = Objects.isNull(result) ? "空数据" : result.toString();
                    out("线程编号[" + threadNo + "]使用数据[" + originData + "]交换得到结果[" + resultData + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static class CountDownLatchDemo {
        public void execute() {
            int subThreadLimitNum = commonLimitThreadThreshold << 1;
            final CountDownLatch countDownLatch = getCountDownLatch(subThreadLimitNum);
            String taskDescription;
            for (int i = 1; i <= subThreadLimitNum; i++) {
                if (i % 2 == 0) {
                    taskDescription = "[偶数]任务" + i;
                } else {
                    taskDescription = "[奇数]任务" + i;
                }
                executorService.execute(new CountDownLatchRunnable(countDownLatch, i, taskDescription));
            }
            try {
                Thread.sleep(getRandomWaitTime(1));
                out("主线程HangOut一丢丢, 等待子线程执行任务完成");
                countDownLatch.await();
                out("全部子线程都执行完任务, 主线程霸气回归, 脉动回来!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public CountDownLatch getCountDownLatch(int subThreadLimitNum) {
            if (subThreadLimitNum < 1) {
                subThreadLimitNum = commonLimitThreadThreshold;
            }
            return new CountDownLatch(subThreadLimitNum);
        }

        class CountDownLatchRunnable implements Runnable {
            private CountDownLatch countDownLatch;
            private int threadNo;
            private String taskDescription;

            public CountDownLatchRunnable(CountDownLatch countDownLatch, int threadNo, String taskDescription) {
                this.countDownLatch = countDownLatch;
                this.threadNo = threadNo;
                this.taskDescription = taskDescription;
            }

            @Override
            public void run() {
                try {
                    out("子线程编号[" + threadNo + "]正在执行任务: " + this.taskDescription);
                    Thread.sleep(getRandomWaitTime(1));
                    out("子线程编号[" + threadNo + "]任务[" + this.taskDescription + "]执行完成");
                    this.countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
