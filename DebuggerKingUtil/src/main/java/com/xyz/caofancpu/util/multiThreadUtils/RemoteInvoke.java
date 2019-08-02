package com.xyz.caofancpu.util.multiThreadUtils;

@FunctionalInterface
public interface RemoteInvoke<T> {
    T invoke();
}
