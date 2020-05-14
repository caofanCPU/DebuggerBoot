package com.xyz.caofancpu.trackingtime.interceptor;

import com.xyz.caofancpu.mvc.interceptor.D8WebLogAspectSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * HTTP接口日志切面
 *
 * @author D8GER
 */
@Component
@Aspect
@Order(2)
public class WebLogAspect extends D8WebLogAspectSupport {

    public WebLogAspect() {
        super(LoggerFactory.getLogger("INFO_FILE"), LoggerFactory.getLogger("ERROR_FILE"));
    }

    /**
     * Http切面
     */
    @Pointcut("execution(public * com.xyz..*.controller..*Controller.*(..))")
    public void webLog() {
        // do something
        /**
         * try {
         *     try {
         *         // 对应@Before注解的方法切面逻辑
         *         doBefore();
         *         method.invoke();
         *     } finally {
         *       // 对应@After注解的方法切面逻辑
         *         doAfter();
         *     }
         *     // 对应@AfterReturning注解的方法切面逻辑
         *     doAfterReturning();
         * } catch (Exception e) {
         *     //对应@AfterThrowing注解的方法切面逻辑
         *     doAfterThrowing();
         * }
         */
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        super.doBefore(joinPoint);
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        return super.doAround(proceedingJoinPoint);
    }

    @AfterThrowing(value = "webLog()", throwing = "ex")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
        super.doAfterThrowingAdvice(joinPoint, ex);
    }

    @AfterReturning(value = "webLog()", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, Object returnValue) {
        super.doAfterReturning(joinPoint, returnValue);
    }
}
