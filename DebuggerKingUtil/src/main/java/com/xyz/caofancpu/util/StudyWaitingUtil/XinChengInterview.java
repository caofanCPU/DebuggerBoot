package com.xyz.caofancpu.util.StudyWaitingUtil;

/**
 * Copyright (C), 2000-2019, 帝八哥科技无限股份有限公司
 * FileName: XinChengInterview
 * Author:   CY_XYZ
 * Date:     2019/3/20 19:09
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class XinChengInterview {
    
    public static void main(String[] args) {
        testThread();

//        testInstanceContruct();
        
    }
    
    
    public static void out(String text) {
        System.out.println(text);
    }
    
    public static void testThread() {
        Thread sxThread = new Thread(() -> {
            out(Thread.currentThread().getName() + " 傻逼");
        });
        // 线程普通方法调用
        sxThread.run();
        
        // 调用子线程执行run方法
        sxThread.start();
        out("线程");
    }
    
    public static void testInstanceContruct() {
        TestInstanceSunB sunB = new TestInstanceSunB();
        out("----------分界线----------");
        sunB = new TestInstanceSunB();
        sunB.conclusion();
    }
    
    static class TestInstanceParentA {
        
        public static int initValue = 20;
        
        static {
            out("父类静态代码块执行之前的初始值为：" + initValue);
            out("父类静态代码块执行之后的初始值为：" + (initValue += 1));
        }
        
        public TestInstanceParentA() {
            out("父类构造函数执行前的初始值为：" + initValue);
            out("父类构造函数执行后的初始值为：" + (initValue += 10));
        }
        
    }
    
    
    static class TestInstanceSunB extends TestInstanceParentA {
        
        static {
            out("子类静态代码块执行之前的初始值为：" + initValue);
            out("子类静态代码块执行之后的初始值为：" + (initValue += 100));
        }
        
        public TestInstanceSunB() {
            out("子类构造函数执行前的初始值为：" + initValue);
            out("子类构造函数执行后的初始值为：" + (initValue += 1000));
        }
        
        public static void conclusion() {
            out("继承机制下初始化子类的结论：\n"
                    + "1.首先是父类静态代码块->子类静态代码块->父类构造函数->子类构造函数\n"
                    + "2.此后便是父类构造函数->子类构造函数，因为静态代码块只会被执行一次，但是变量值是静态的，因而会一直保存！"
            );
            
            out("父类静态代码块执行之前的初始值为：20\n" +
                    "父类静态代码块执行之后的初始值为：21\n" +
                    "子类静态代码块执行之前的初始值为：21\n" +
                    "子类静态代码块执行之后的初始值为：121\n" +
                    "父类构造函数执行前的初始值为：121\n" +
                    "父类构造函数执行后的初始值为：131\n" +
                    "子类构造函数执行前的初始值为：131\n" +
                    "子类构造函数执行后的初始值为：1131\n" +
                    "----------分界线----------\n" +
                    "父类构造函数执行前的初始值为：1131\n" +
                    "父类构造函数执行后的初始值为：1141\n" +
                    "子类构造函数执行前的初始值为：1141\n" +
                    "子类构造函数执行后的初始值为：2141"
            );
        }
        
    }
    
}
