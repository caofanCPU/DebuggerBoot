package com.xyz.caofancpu.util.multiThreadUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RemoteServiceHelper<T, K, M> implements RemoteService {
    
    public RemoteServiceHelper(Object serviceInstance, String methodName, Object param, Class<?>... paramClass) {
        this.serviceInstance = serviceInstance;
        this.methodName = methodName;
        this.paramClass = paramClass;
        this.params = param;
    }
    
    private Object serviceInstance;
    private Object params;
    private String methodName;
    private Class<?>[] paramClass;
    
    @Override
    public M invoke() {
        Method method = getInvokeMethod();
        if (method != null) {
            try {
                Object obj = method.invoke(serviceInstance, params);
//                System.out.println(JSONUtil.serializeJSON(obj));
                return (M) obj;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public Object getServiceInstance() {
        return serviceInstance;
    }
    
    public void setServiceInstance(T serviceInstance) {
        this.serviceInstance = serviceInstance;
    }
    
    public Object getParams() {
        return params;
    }
    
    public void setParams(K params) {
        this.params = params;
    }
    
    public String getMethodName() {
        return methodName;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public Method getInvokeMethod() {
        try {
            return serviceInstance.getClass().getMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
