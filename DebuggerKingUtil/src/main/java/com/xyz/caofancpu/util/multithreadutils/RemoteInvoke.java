package com.xyz.caofancpu.util.multithreadutils;

@FunctionalInterface
public interface RemoteInvoke<T> {
    T invoke();
}
