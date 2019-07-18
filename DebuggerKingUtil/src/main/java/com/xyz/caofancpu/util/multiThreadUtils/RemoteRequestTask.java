package com.xyz.caofancpu.util.multiThreadUtils;

import java.util.concurrent.Callable;

public class RemoteRequestTask<K> implements Callable<K> {
    private RemoteService remoteService;

    public RemoteRequestTask(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    public RemoteService getRemoteService() {
        return remoteService;
    }

    public void setRemoteService(RemoteServiceHelper remoteService) {
        this.remoteService = remoteService;
    }

    @Override
    public K call() {
        K k = (K) remoteService.invoke();
        return k;
    }
}
