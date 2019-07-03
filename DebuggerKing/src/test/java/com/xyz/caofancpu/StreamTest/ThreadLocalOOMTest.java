package com.xyz.caofancpu.StreamTest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * FileName: ThreadLocalOOMTest
 *
 * @author: caofanCPU
 * @date: 2019/3/11 15:03
 */

public class ThreadLocalOOMTest {
    private static final int THREAD_LOOP_SIZE = 500;
    private static final int MAX_DATA_LOOP_SIZE = 10000;
    
    private static ThreadLocal<List<User>> threadLocal = new ThreadLocal<>();
    
    public static void main(String[] args) {
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_LOOP_SIZE);
        IntStream.range(1, THREAD_LOOP_SIZE + 1)
                .forEach((i) -> {
                    executorService.execute(() -> {
                        threadLocal.set(new ThreadLocalOOMTest().addBigList());
                        System.out.println(Thread.currentThread().getName());
                        threadLocal.remove();
                    });
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        executorService.shutdown();
    }
    
    private List<User> addBigList() {
        List<User> userList = new ArrayList<>(MAX_DATA_LOOP_SIZE);
        IntStream.range(1, MAX_DATA_LOOP_SIZE + 1)
                .forEach(i -> userList.add(new User("XYB" + i, "mima" + i, "nv" + i, i)));
        return userList;
    }

    @Data
    @AllArgsConstructor
    class User {
        private String userName;
        private String passWord;
        private String sex;
        private int age;
    }
    
}
