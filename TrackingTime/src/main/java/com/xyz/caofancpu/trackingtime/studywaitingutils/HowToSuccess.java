package com.xyz.caofancpu.trackingtime.studywaitingutils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HowToSuccess {
    public static void main(String[] args)
            throws InterruptedException {
        test3();
    }

    public static void test3() {
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        new Thread(() -> {
            rtLock.writeLock().lock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("WriteLockAAAAA");
//            rtLock.writeLock().unlock();
        }).start();

        new Thread(() -> {
            rtLock.readLock().lock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ReadLockBBBBBBB");
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
}
