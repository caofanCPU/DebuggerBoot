package com.xyz.caofancpu.trackingtime.utils;

import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import com.xyz.caofancpu.util.result.GlobalErrorInfoEnum;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by caofanCPU on 2018/8/6.
 */
@Component
@Slf4j
public class RestTemplateUtil2 {
    /**
     * 对于多个restTemplate的情形, 使用@Autowired + @Qualifier注解
     * 使用服务名访问
     */
    @Autowired
    @Qualifier(value = "restTemplate")
    private RestTemplate restTemplate;

    /**
     * 对于多个restTemplate的情形, 使用@Autowired + @Qualifier注解
     * 使用IP:port访问
     */
    @Autowired
    @Qualifier(value = "zuulRestTemplate")
    private RestTemplate zuulRestTemplate;

    @Value("${authorization.user-profile.key}")
    private String authKey;

    @Value("${fileOperate.logging.key}")
    private String fileOperateLoggingKey;

    @Value("${resource_access_key}")
    private String resourceAccessKey;

    /**
     * 剔除请求中值为null的参数
     *
     * @param paramsMap
     * @return
     */
    public Map<String, Object> removeNullElement(Map<String, Object> paramsMap) {
        if (paramsMap == null || paramsMap.isEmpty()) {
            return new HashMap<>(1, 0.5f);
        }
        /** 一般请求参数不会太多，故而使用单向顺序流即可 */
        // 1.首先构建流，剔除值为空的元素
        Stream<Map.Entry<String, Object>> tempStream = paramsMap.entrySet().stream()
                .filter((entry) -> entry.getValue() != null);
        // 2.从流中恢复map
        Map<String, Object> resultMap = tempStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        log.info("完成请求参数中null值的剔除");
        return resultMap;
    }

    public ResultBody convertResponse(String url, JSONObject responseJson) {
        ResultBody resultBody = new ResultBody();
        boolean msgFlag = Objects.isNull(responseJson.get("msg"));
        if (!GlobalErrorInfoEnum.SUCCESS.getCode().equals(String.valueOf(responseJson.get("code")))) {
            resultBody.setData(null);
            resultBody.setCode(GlobalErrorInfoEnum.GLOBAL_MS_MSG.getCode());
            if (msgFlag) {
                resultBody.setMsg(GlobalErrorInfoEnum.GLOBAL_MS_MSG.getMsg());
            } else {
                resultBody.setMsg(responseJson.get("msg").toString() + "[MSR]");
            }
            // 对于接口调用返回非200的情况, 记录日志
            log.error("调用微服务接口: [{}], 响应信息: [{}]", url, resultBody.getMsg());
        } else {
            resultBody.setData(responseJson.get("data"));
            resultBody.setCode(GlobalErrorInfoEnum.SUCCESS.getCode());
            if (msgFlag) {
                resultBody.setMsg(GlobalErrorInfoEnum.SUCCESS.getMsg());
            } else {
                resultBody.setMsg(responseJson.get("msg").toString() + "[MSR]");
            }
        }
        return resultBody;
    }

    /**
     * GET传参数，拼接方式
     *
     * @param url
     * @param paramMap
     * @return
     */
    public ResultBody getParam(String url, Map<String, Object> paramMap, String msErrorMsg)
            throws GlobalErrorInfoException {
        Map<String, Object> requestMap = removeNullElement(paramMap);
        String token = loadToken();
        String requestLog = showRequestLog(url, requestMap, token, HttpMethod.GET);

        HttpEntity<Map<String, Object>> httpEntity = loadHttpEntity(new HashMap<>(2, 0.5f), token);
        ResponseEntity<JSONObject> responseEntity = null;
        String requestUrl = loadUrl(url, requestMap);
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, JSONObject.class);
        } catch (Exception e) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, e.getMessage());
            handleMSErrorMsg(msErrorMsg);
        }
        if (responseEntity == null) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, "响应为null");
            return handleMSErrorResult(msErrorMsg);
        }
        JSONObject responseJson = responseEntity.getBody();
        String responseLog = showResponseLog(url, responseJson.toJSONString());
        return convertResponse(url, responseJson);
    }

    /**
     * POST方式调用，传body对象
     *
     * @param url
     * @param paramMap
     * @return
     */
    public ResultBody postBody(String url, Map<String, Object> paramMap, String msErrorMsg)
            throws GlobalErrorInfoException {
        Map<String, Object> bodyMap = removeNullElement(paramMap);
        String token = loadToken();
        String requestLog = showRequestLog(url, bodyMap, token, HttpMethod.POST);
        // CAT监控:MS请求埋点, 一定要在加载CATHeaders之前执行

        HttpEntity<Map<String, Object>> httpEntity = loadHttpEntity(paramMap, token);
        JSONObject responseJson = null;
        try {
            responseJson = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        } catch (Exception e) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, e.getMessage());
            handleMSErrorMsg(msErrorMsg);
        }
        if (responseJson == null) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, "响应为null");
            return handleMSErrorResult(msErrorMsg);
        }
        String responseLog = showResponseLog(url, responseJson.toJSONString());
        return convertResponse(url, responseJson);
    }

    /**
     * 通过IP:port访问 POST方式调用，传body对象
     *
     * @param url
     * @param paramMap
     * @return
     */
    public ResultBody zuulPostBody(String url, Map<String, Object> paramMap, HttpHeaders headers, String msErrorMsg)
            throws GlobalErrorInfoException {
        Map<String, Object> bodyMap = removeNullElement(paramMap);
        String token = loadToken();
        String requestLog = showRequestLog(url, bodyMap, token, HttpMethod.POST);
        HttpEntity<Map<String, Object>> httpEntity;
        if (Objects.nonNull(headers)) {
            httpEntity = new HttpEntity<>(bodyMap, headers);
        } else {
            httpEntity = loadHttpEntity(bodyMap, token);
        }
        JSONObject responseJson = null;
        try {
            responseJson = zuulRestTemplate.postForObject(url, httpEntity, JSONObject.class);
        } catch (Exception e) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, e.getMessage());
            handleMSErrorMsg(msErrorMsg);
        }
        if (responseJson == null) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, "响应为null");
            return handleMSErrorResult(msErrorMsg);
        }
        String responseLog = showResponseLog(url, responseJson.toJSONString());
        return convertResponse(url, responseJson);
    }

    /**
     * POST方式调用，传body对象, 对象为List
     *
     * @param url
     * @param paramList
     * @return
     */
    public ResultBody postListBody(String url, List<?> paramList, String msErrorMsg)
            throws GlobalErrorInfoException {
        List<?> filteredList = paramList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        String token = loadToken();
        Map<String, Object> bodyLoggingMap = new HashMap<String, Object>(4, 0.5f) {
            {
                put("List<?>", paramList);
            }
        };

        String requestLog = showRequestLog(url, bodyLoggingMap, token, HttpMethod.POST);
        HttpEntity<List<?>> httpEntity = loadListHttpEntity(filteredList, token);
        JSONObject responseJson = null;
        try {
            responseJson = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        } catch (Exception e) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, e.getMessage());
            handleMSErrorMsg(msErrorMsg);
        }
        if (responseJson == null) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, "响应为null");
            return handleMSErrorResult(msErrorMsg);
        }
        String responseLog = showResponseLog(url, responseJson.toJSONString());
        return convertResponse(url, responseJson);
    }


    /**
     * GET请求，把参数拼接到URL之后
     * 示例： http://xxx/{3}/name=debugger&age=20&money=100.25
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public String loadUrl(String url, Map<String, Object> paramsMap) {
        final String paramSymbol = "?";
        final String connectSymbol = "&";
        final String equalSymbol = "=";
        if (StringUtils.isBlank(url) || paramsMap.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        // 1.拼接资源ID
        if (paramsMap.get(resourceAccessKey) != null) {
            sb.append(paramsMap.get(resourceAccessKey));
        }
        // 2.拼接'?'
        sb.append(paramSymbol);
        // 3.遍历参数进行拼接：参数名['=']值['&']
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        // GET传参一般不会很多，故而使用单向顺序流即可
        entrySet.stream()
                .forEach(entry -> sb.append(entry.getKey())
                        .append(equalSymbol)
                        .append(entry.getValue())
                        .append(connectSymbol)
                );
        // 4.移除最后一个多余的'&'
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void handleMSErrorMsg(String msErrorMsg)
            throws GlobalErrorInfoException {
        if (Objects.isNull(msErrorMsg)) {
            msErrorMsg = GlobalErrorInfoEnum.GLOBAL_MS_MSG.getMsg();
        }
        throw new GlobalErrorInfoException(msErrorMsg);
    }

    private ResultBody handleMSErrorResult(String msErrorMsg) {
        if (Objects.isNull(msErrorMsg)) {
            msErrorMsg = GlobalErrorInfoEnum.GLOBAL_MS_MSG.getMsg();
        }
        return new ResultBody().fail(GlobalErrorInfoEnum.GLOBAL_MS_MSG.getCode(), msErrorMsg);
    }

    /**
     * 封装请求对象HttpEntity，主要是token、请求参数
     *
     * @param paramMap
     * @param token
     * @return
     */
    public HttpEntity<Map<String, Object>> loadHttpEntity(Map<String, Object> paramMap, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Content-Type", "application/json");
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);
        return httpEntity;
    }

    /**
     * 封装请求对象HttpEntity，主要是token、请求参数
     *
     * @param paramList
     * @param token
     * @return
     */
    public HttpEntity<List<?>> loadListHttpEntity(List<?> paramList, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Content-Type", "application/json");
        HttpEntity<List<?>> httpEntity = new HttpEntity<>(paramList, headers);
        return httpEntity;
    }

    /**
     * 打印请求参数
     *
     * @param url
     * @param paramMap
     * @param token
     * @return
     */
    public String showRequestLog(String url, Map<String, Object> paramMap, String token, HttpMethod httpMethod) {
        // 针对文件上传, 设置不打印LOG
        if (Objects.nonNull(paramMap.get(fileOperateLoggingKey))
                && Boolean.FALSE.toString().equals(paramMap.get(fileOperateLoggingKey))) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n[请求微服务接口]:\n请求url=")
                .append(url)
                .append("\n请求方式=")
                .append(StringUtils.upperCase(httpMethod.name()))
                .append("\n请求参数paramsMap=")
                .append(JSONUtil.formatStandardJSON(new JSONObject(paramMap).toJSONString()))
                .append("\n携带token=")
                .append(token);
        log.info(sb.toString());
        return sb.toString();
    }

    /**
     * 判空处理
     *
     * @return
     */
    public String loadToken() {
        RequestAttributes temRequestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(temRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes sra = (ServletRequestAttributes) temRequestAttributes;
        RequestContextHolder.setRequestAttributes(sra, true);
        HttpServletRequest request;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.info("[提示]无HttpServletRequest信息, 加载token为null");
            return null;
        }
        if (Objects.isNull(request)) {
            return null;
        }
        String token = request.getHeader(authKey);
        return token;
    }

    /**
     * 打印响应结果
     *
     * @param jsonString
     * @return
     */
    public String showResponseLog(String url, String jsonString) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n[微服务响应]:\n接口url=")
                .append(url)
                .append("\n接口响应:\n")
                .append(JSONUtil.formatStandardJSON(jsonString));
        log.info(sb.toString());
        return sb.toString();
    }


}
