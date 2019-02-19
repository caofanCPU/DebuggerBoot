package com.xyz.caofancpu.utils;

/**
 * FileName: MyGarbage
 *
 * @author: caofanCPU
 * @date: 2019/2/19 12:12
 */

public class MyGarbage {
    
    public static void main(String[] args) {
        int defaultCap = 10;
        int cap = defaultCap;
        for (int i = 1; i < 5; i++) {
            cap += (cap >> 1);
            out(cap + "\n");
        }
    }
    
    public static void out(String text) {
        System.out.println(text);
        
    }
    
}
