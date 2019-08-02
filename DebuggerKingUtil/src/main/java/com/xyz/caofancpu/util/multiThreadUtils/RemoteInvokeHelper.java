package com.xyz.caofancpu.util.multiThreadUtils;

import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
@Accessors(chain = true)
public class RemoteInvokeHelper<T, K, M> implements RemoteInvoke {
    private static final Logger logger = LoggerFactory.getLogger(RemoteInvokeHelper.class);

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
                logger.error("远程调用反射出错, 原因: {}", e);
            } catch (InvocationTargetException e) {
                logger.error("远程调用反射出错, 原因: {}", e);
            }
        }
        return null;
    }

    private Method getInvokeMethod() {
        try {
            return serviceInstance.getClass().getMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            logger.error("远程调用反射出错, 原因: {}", e);
            throw new RuntimeException("远程调用反射出错");
        }
    }
}
