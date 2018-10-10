package com.xyz.caofancpu.utils;

/**
 * Created by caofanCPU on 2018/7/3.
 *
 * @author
 */
public class NumberUtil {
    
    /**
     * 获取指定位数的随机数
     *
     * @param digit
     * @return
     */
    public static Integer getInteger(Integer digit) {
        return (int) ((Math.random() * 9 + 1) * Math.pow(10, digit - 1));
    }
    
    public static void main(String[] args) {
        for (int version = 1; version < 100; version++) {
            String result = (version / 10 + (version % 10 == 0 ? 0 : 1)) + "." + (version - 1) % 10;
            System.out.println("数值：" + version + "\t版本号：" + result);
        }
        
    }
    
}
