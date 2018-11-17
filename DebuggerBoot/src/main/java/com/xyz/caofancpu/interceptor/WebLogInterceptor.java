package com.xyz.caofancpu.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import com.xyz.caofancpu.utils.DataHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Aspect
public class WebLogInterceptor {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    /**
     * 日志切面
     */
    @Pointcut("execution(public * com.xyz..*.controller.*Controller.*(..))")
    public void webLog() {
        // do something
    }
    
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestInterface = joinPoint.getSignature().getDeclaringTypeName()
                + "."
                + joinPoint.getSignature().getName();
        String requestParam = JSONUtil.formatStandardJSON(JSONObject.toJSONString(DataHelper.getParameterMap(request)));
        String requestBody;
        try {
            requestBody = JSONUtil.formatStandardJSON(JSONObject.toJSONString(joinPoint.getArgs()));
        } catch (Exception e) {
            logger.info("入参为文件(InputStreamSource)或HttpRequest等类型, 打印对象地址信息");
            requestBody = joinPoint.getArgs().toString();
        }
        logger.info("\n请求IP=" + getIpAddress(request) + "\n"
                + "请求方式=" + request.getMethod() + "\n"
                + "请求地址=" + request.getRequestURL().toString() + "\n"
                + "请求接口=" + requestInterface + "\n"
                + "请求Param参数=" + requestParam + "\n"
                + "请求Body对象=" + requestBody + "\n"
        );
    }
    
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 处理完请求，返回内容
        logger.info("\n[后台响应结果]:\n"
                + "响应耗时[" + (System.currentTimeMillis() - startTime) + "ms]" + "\n"
                + "响应数据结果:\n"
                + JSONUtil.formatStandardJSON(JSONObject.toJSONString(result))
        );
        return result;
    }
    
    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
        // do something
    }
    
    /**
     * 获取真实IP
     *
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    logger.error("获取IP异常 : {}", e);
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}