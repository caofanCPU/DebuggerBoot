package com.xyz.caofancpu.trackingtime.studywaitingutils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HowToSuccess {
    public static void main(String[] args)
            throws InterruptedException {
        test3P2();
    }

    public static void test3P2() {
        ReentrantLock lock = new ReentrantLock(true);
        Condition firstA = lock.newCondition();
        Condition secondB = lock.newCondition();
        Condition lastC = lock.newCondition();
        AtomicInteger count = new AtomicInteger(0);
        new MyReentrantLockThread(lock, firstA, secondB, count, "A", 0).start();
        new MyReentrantLockThread(lock, secondB, lastC, count, "B", 1).start();
        new MyReentrantLockThread(lock, lastC, firstA, count, "C", 2).start();
    }

    public static void test3P1() {
        Semaphore firstA = new Semaphore(1);
        Semaphore secondB = new Semaphore(0);
        Semaphore lastC = new Semaphore(0);
        new MySemaphoreThread(firstA, secondB, "A").start();
        new MySemaphoreThread(secondB, lastC, "B").start();
        new MySemaphoreThread(lastC, firstA, "C").start();
    }

    public static void test4() {
        Thread thread = new Thread(() -> {
            throw new RuntimeException();
        });
        System.out.println(thread.getState() + ", " + thread.isInterrupted());
        thread.start();
        int i = 1;
        while (++i <= 10) {
            System.out.println(thread.getState() + ", " + thread.isInterrupted());
        }
        /**
         * NEW, false
         * RUNNABLE, false
         * RUNNABLE, false
         * RUNNABLE, false
         * RUNNABLE, false
         * RUNNABLE, false
         * TERMINATED, false
         * TERMINATED, false
         * TERMINATED, false
         * TERMINATED, false
         */
    }

    public static void test1()
            throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                // 响应中断
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程中断标志为中断, 于是才响应中断");
                    return;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("线程睡觉过程中被中断, 被中断后会清除中断状态, 此时中断标志是: " + Thread.currentThread().isInterrupted());
                }

                if (!Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().interrupt();
                    System.out.println("再利用系统给当前自己发送一个中断信号");
                }
            }
        });
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class MyReentrantLockThread extends Thread {
        private ReentrantLock lock;
        private Condition current;
        private Condition next;
        private String content;
        private AtomicInteger count;
        private int specialNum;

        public MyReentrantLockThread(ReentrantLock lock, Condition current, Condition next, AtomicInteger count, String content, int specialNum) {
            this.lock = lock;
            this.current = current;
            this.next = next;
            this.content = content;
            this.count = count;
            this.specialNum = specialNum;
            super.setName("线程-" + content);
        }

        @Override
        public void run() {
            try {
                this.lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (this.count.get() % 3 != specialNum) {
                        this.current.await();
                    }
                    System.out.println("[" + Thread.currentThread().getName() + "]第[" + (i + 1) + "]次打印内容: " + this.content);
                    this.count.incrementAndGet();
                    this.next.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.lock.unlock();
            }
        }
    }

    public static void test3() {
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        new Thread(() -> {
            rtLock.readLock().lock();
            System.out.println("读嘟嘟AAAAA");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("读嘟嘟AAAAA");
            rtLock.readLock().unlock();
        }).start();

        new Thread(() -> {
            rtLock.readLock().lock();
            System.out.println("123");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("456");
            rtLock.readLock().unlock();
        }).start();
    }

    public static void test2() {
        //定义一个计数器，当计数器的值累加到30，输出"放行"
        CyclicBarrier cyclicBarrier = new CyclicBarrier(30, () -> {
            System.out.println("放行");
        });
        for (int i = 1; i <= 90; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println("-->" + temp);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

    public static void hah(Integer a, String... ext) {
        System.out.println("hah(1)和hah(1, new String[0])是等价的");
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class MySemaphoreThread extends Thread {
        private Semaphore current;
        private Semaphore next;
        private String content;

        public MySemaphoreThread(Semaphore current, Semaphore next, String content) {
            this.current = current;
            this.next = next;
            this.content = content;
            super.setName("线程-" + content);
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    this.current.acquire();
                    System.out.println("[" + Thread.currentThread().getName() + "]第[" + (i + 1) + "]次打印内容: " + this.content);
                    this.next.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
