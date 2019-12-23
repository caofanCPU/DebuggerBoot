package com.xyz.caofancpu.util.multithreadutils;

import java.util.concurrent.Callable;

public class RemoteRequestTask<K> implements Callable<K> {
    private RemoteInvoke remoteInvoke;

    public RemoteRequestTask(RemoteInvoke remoteInvoke) {
        this.remoteInvoke = remoteInvoke;
    }

    public RemoteInvoke getRemoteInvoke() {
        return remoteInvoke;
    }

    public void setRemoteInvoke(RemoteInvokeHelper remoteInvoke) {
        this.remoteInvoke = remoteInvoke;
    }

    @Override
    public K call() {
        K k = (K) remoteInvoke.invoke();
        return k;
    }
}
