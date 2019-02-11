Thread的对象, **run方法只是告诉JVM该方法内的代码在新线程执行**, 而**start方法是本地方法, JVM根据此方法完成线程的注册和启动**

Thread的对象, 主线程等待子线程处理结束后才继续执行, **则主线程内创建子线程对象, 执行join(时间参数毫秒)方法**

主线程需要处理子线程的异常,或者说子线程的异常需要抛给主线程, 则将线程替换为任务FutureTask<?>;

对于任务FutureTask<?>, ?代表任务结束后返回的数据类型;

如果主线程需要等待子任务FutureTask<?>执行完毕后才继续, 则相应替换为futureTask.get()方法

主线程调用子任务FutureTask的get方法分以下三种情况:

1. 子任务未启动时, 阻塞主线程

2. 子任务已启动时, 阻塞主线程

3. 子任务已完成时, 立刻返回任务执行结果

从中可看出, FutureTask的状态为 未启动-->已启动-->已完成

主线程不知道子任务处于哪个状态, 所以调用子任务Futuretask的get方法时, 需要处理异常

```
...
主线程代码, 启动子任务后, 继续下面
try {
    for循环(){
        futureTaskArray[i].get(); // 可传等待的毫秒时间参数
    }
} catch (Exception e) {
    if (e instanceof Exception && e.getCause() instanceof 自定义异常) {
        throw (自定义异常) e;
    }
    throw new 封装异常("...");
}
```

或者使用try-catch-catch多段式处理, 代码更清晰


