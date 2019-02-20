package com.xyz.caofancpu.utils;

/**
 * FileName: MyGarbage
 *
 * @author: caofanCPU
 * @date: 2019/2/19 12:12
 */

public class MyGarbage {
    
    public static void main(String[] args) {
        int n = 16;
        n = n - (n >>> 2);
        out(n + "");
    }
    
    public static void out(String text) {
        System.out.println(text);
    }
    
}
