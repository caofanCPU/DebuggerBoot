package com.xyz.caofancpu.util.multithreadutils;

/**
 * 函数式接口，支持计算比率
 */
@FunctionalInterface
public interface OnUpdateRate {
    void updateRate(int total, int current);
}
