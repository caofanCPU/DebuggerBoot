package com.xyz.caofancpu.interceptor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * FileName: MapperAspect
 */
@Aspect
@Component
public class ServiceAspect {

    @Around(value = "execution(* com.xyz..*.service.imp..*ServiceImpl.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjp)
            throws Throwable {
        Object result;
        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();
        Transaction t = Cat.newTransaction("MVC", method.getName());
        try {
            result = pjp.proceed();
            t.setStatus(Transaction.SUCCESS);
            t.complete();
        } catch (Throwable e) {
            t.setStatus(e);
            Cat.logError(e);
            t.complete();
            throw e;
        }
        return result;
    }
}
