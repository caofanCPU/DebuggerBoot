package com.xyz.caofancpu.utils;

import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.result.GlobalErrorInfoEnum;
import com.xyz.caofancpu.result.GlobalErrorInfoException;
import com.xyz.caofancpu.result.ResultBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by caofanCPU on 2018/8/6.
 */
@Component
public class RestTemplateUtil {
    
    private static Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);
    
    @Value("${authorization.user-profile.key}")
    private String authKey;
    
    @Resource(type = RestTemplate.class)
    private RestTemplate restTemplate;
    
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
        logger.info("完成请求参数中null值的剔除");
        return resultMap;
    }
    
    public ResultBody convertResponse(JSONObject responseJson) {
        ResultBody resultBody = new ResultBody();
        boolean msgFlag = (responseJson.get("msg") == null);
        if (!GlobalErrorInfoEnum.SUCCESS.getCode().equals(String.valueOf(responseJson.get("code")))) {
            resultBody.setData(null);
            resultBody.setCode(GlobalErrorInfoEnum.CALL_SERVICE_ERROR.getCode());
            if (msgFlag) {
                resultBody.setMsg(GlobalErrorInfoEnum.CALL_SERVICE_ERROR.getMsg());
            } else {
                resultBody.setMsg(responseJson.get("msg").toString());
            }
        } else {
            resultBody.setData(responseJson.get("data"));
            resultBody.setCode(GlobalErrorInfoEnum.SUCCESS.getCode());
            if (msgFlag) {
                resultBody.setMsg(GlobalErrorInfoEnum.SUCCESS.getMsg());
            } else {
                resultBody.setMsg(responseJson.get("msg").toString());
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
    public ResultBody getParam(String url, Map<String, Object> paramMap)
            throws GlobalErrorInfoException {
        Map<String, Object> requestMap = removeNullElement(paramMap);
        String token = loadToken();
        showRequestLog(url, requestMap, token);
        HttpEntity<Map<String, Object>> httpEntity = loadHttpEntity(new HashMap<>(1, 0.5f), token);
        ResponseEntity<JSONObject> responseEntity;
        try {
            String requestUrl = loadUrl(url, requestMap);
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, JSONObject.class);
            if (responseEntity == null) {
                logger.error("调用微服务接口失败：{}", url + "\n响应为null");
            }
        } catch (RestClientException e) {
            logger.error("调用微服务接口失败：{}", url + "\n" + e.getMessage());
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.CALL_SERVICE_ERROR);
        }
        if (responseEntity == null) {
            return new ResultBody(GlobalErrorInfoEnum.CALL_SERVICE_ERROR);
        }
        JSONObject responseJson = responseEntity.getBody();
        showResponseLog(responseJson.toJSONString());
        return convertResponse(responseJson);
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
        if (StringUtils.isEmpty(url) || paramsMap.isEmpty()) {
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
        entrySet.stream().forEach((entry) -> sb.append(entry.getKey())
                .append(equalSymbol)
                .append(entry.getValue())
                .append(connectSymbol)
        );
        // 4.移除最后一个多余的'&'
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
    /**
     * POST方式调用，传body对象
     *
     * @param url
     * @param paramMap
     * @return
     */
    public ResultBody postBody(String url, Map<String, Object> paramMap)
            throws GlobalErrorInfoException {
        Map<String, Object> bodyMap = removeNullElement(paramMap);
        String token = loadToken();
        showRequestLog(url, bodyMap, token);
        HttpEntity<Map<String, Object>> httpEntity = loadHttpEntity(paramMap, token);
        JSONObject responseJson;
        try {
            responseJson = restTemplate.postForObject(url, httpEntity, JSONObject.class);
            if (responseJson == null) {
                logger.error("调用微服务接口失败：{}", url + "\n响应为null");
            }
        } catch (RestClientException e) {
            logger.error("调用微服务接口失败：{}", url + "\n" + e.getMessage());
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.CALL_SERVICE_ERROR);
        }
        if (responseJson == null) {
            return new ResultBody(new GlobalErrorInfoException(GlobalErrorInfoEnum.CALL_SERVICE_ERROR));
        }
        showResponseLog(responseJson.toJSONString());
        return convertResponse(responseJson);
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
     * 打印请求参数
     *
     * @param url
     * @param paramMap
     * @param token
     */
    public void showRequestLog(String url, Map<String, Object> paramMap, String token) {
        logger.info("\n\n\n"
                + "请求url=" + url + "\n"
                + "请求参数paramsMap=" + JSONUtil.formatStandardJSON(new JSONObject(paramMap).toJSONString()) + "\n"
                + "携带token=" + token
                + "\n\n\n");
    }
    
    public String loadToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(authKey);
        return token;
    }
    
    /**
     * 打印响应结果
     *
     * @param jsonString
     */
    public void showResponseLog(String jsonString) {
        logger.info("\n\n\n" + "接口响应：" + JSONUtil.formatStandardJSON(jsonString) + "\n\n\n");
    }
    
    
}
