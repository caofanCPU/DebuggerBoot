package com.xyz.caofancpu.util.multithreadutils;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
@Accessors(chain = true)
@Slf4j
public class RemoteInvokeHelper<T, K, M> implements RemoteInvoke {

    private Object serviceInstance;
    private Object params;
    private String methodName;
    private Class<?>[] paramClass;

    public RemoteInvokeHelper(Object serviceInstance, String methodName, Object param, Class<?>... paramClass) {
        this.serviceInstance = serviceInstance;
        this.methodName = methodName;
        this.paramClass = paramClass;
        this.params = param;
    }

    @Override
    public M invoke() {
        Method method = getInvokeMethod();
        if (method != null) {
            try {
                Object obj = method.invoke(serviceInstance, params);
                return (M) obj;
            } catch (IllegalAccessException e) {
                log.error("远程调用反射出错, 原因: {}", e);
            } catch (InvocationTargetException e) {
                log.error("远程调用反射出错, 原因: {}", e);
            }
        }
        return null;
    }

    private Method getInvokeMethod() {
        try {
            return serviceInstance.getClass().getMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            log.error("远程调用反射出错, 原因: {}", e);
            throw new RuntimeException("远程调用反射出错");
        }
    }
}
