package com.xyz.caofancpu.trackingtime.interceptor;

import com.xyz.caofancpu.trackingtime.utils.DataHelper;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import com.xyz.caofancpu.util.streamOperateUtils.StreamUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Component
@Aspect
@Order(2)
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 日志切面
     */
    @Pointcut("execution(public * com.xyz..*.controller..*Controller.*(..))")
    public void webLog() {
        // do something
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestInterface = proceedingJoinPoint.getSignature().getDeclaringTypeName()
                + "."
                + proceedingJoinPoint.getSignature().getName();
        // 入参为文件时, 不打印log
        Map<String, Object> originRequestParamMap = DataHelper.getParameterMap(request);
        Map<String, Object> filteredFileValueMap = StreamUtil.removeSpecifiedElement(originRequestParamMap,
                new Class[]{MultipartFile.class, File.class});
        String requestParam = JSONUtil.formatStandardJSON(JSONUtil.toJSONStringWithDateFormat(filteredFileValueMap));
        // 入参为文件时, 不打印log
        Object[] originBodyParamArray = proceedingJoinPoint.getArgs();
        Object[] filteredFileValueArray = StreamUtil.removeSpecifiedElement(originBodyParamArray, new Class[]{MultipartFile.class, File.class});
        String requestBody;
        try {
            requestBody = JSONUtil.formatStandardJSON(JSONUtil.toJSONStringWithDateFormat(filteredFileValueArray));
        } catch (Exception e) {
            logger.info("入参为文件(InputStreamSource)或HttpRequest等类型, 打印对象地址信息");
            requestBody = Arrays.toString(proceedingJoinPoint.getArgs());
        }
        StringBuilder requestSb = new StringBuilder();
        requestSb.append("\n[前端页面请求]:\n"
                + "请求IP=" + getIpAddress(request) + "\n"
                + "请求方式=" + request.getMethod() + "\n"
                + "请求地址=" + request.getRequestURL().toString() + "\n"
                + "请求接口=" + requestInterface + "\n"
                + "请求Param参数=" + requestParam + "\n"
                + "请求Body对象=" + requestBody + "\n");
        logger.info(requestSb.toString());
        // 开始时间
        long startTime = System.currentTimeMillis();
        // 耗时, 字符串标识, @#为了便于标志区分
        final String execTime = "@Time#";
        Object result;

        StringBuilder responseSb = new StringBuilder();
        responseSb.append("\n[后台响应结果]:\n"
                + "URL地址=" + request.getRequestURL().toString() + "\n"
                + "后台接口=" + requestInterface + "\n"
                + "响应耗时[" + execTime + "ms]" + "\n"
                + "响应数据结果:\n"
        );
        result = proceedingJoinPoint.proceed();
        // 处理完请求，返回内容
        responseSb.append(JSONUtil.formatStandardJSON(JSONUtil.toJSONStringWithDateFormat(result)));
        logger.info(responseSb.toString().replace(execTime, String.valueOf(System.currentTimeMillis() - startTime)));
        return result;
    }

    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {

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
                if (Objects.nonNull(inet)) {
                    ipAddress = inet.getHostAddress();
                }
            }
        }
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}