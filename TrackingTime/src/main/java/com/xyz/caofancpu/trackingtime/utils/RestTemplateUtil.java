package com.xyz.caofancpu.trackingtime.utils;

import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import com.xyz.caofancpu.util.result.GlobalErrorInfoEnum;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by caofanCPU on 2018/8/6.
 */
@Component
@Slf4j
public class RestTemplateUtil {
    @Value("${authorization.user-profile.key}")
    private String authKey;

    @Value("${fileOperate.logging.key}")
    private String fileOperateLoggingKey;

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
        Stream<Map.Entry<String, Object>> tempStream = paramsMap.entrySet().stream().filter((entry) -> entry.getValue() != null);
        // 2.从流中恢复map
        Map<String, Object> resultMap = tempStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        log.info("完成请求参数中null值的剔除");
        return resultMap;
    }

    public ResultBody convertResponse(JSONObject responseJson) {
        ResultBody resultBody = new ResultBody();
        boolean msgFlag = responseJson.get("msg") == null;
        if (!GlobalErrorInfoEnum.SUCCESS.getCode().equals(String.valueOf(responseJson.get("code")))) {
            resultBody.setData(null);
            resultBody.setCode(GlobalErrorInfoEnum.GLOBAL_MS_MSG.getCode());
            if (msgFlag) {
                resultBody.setMsg(GlobalErrorInfoEnum.GLOBAL_MS_MSG.getMsg());
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
        showRequestLog(url, requestMap, token, HttpMethod.GET);
        HttpEntity<Map<String, Object>> httpEntity = loadHttpEntity(new HashMap<>(1, 0.5f), token);
        ResponseEntity<JSONObject> responseEntity;
        try {
            String requestUrl = loadUrl(url, requestMap);
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, JSONObject.class);
        } catch (RestClientException e) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, e.getMessage());
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.GLOBAL_MS_MSG);
        }
        if (responseEntity == null) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, "响应为null");
            return new ResultBody(GlobalErrorInfoEnum.GLOBAL_MS_MSG);
        }
        JSONObject responseJson = responseEntity.getBody();
        showResponseLog(url, responseJson.toJSONString());
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
        // GET传参一般不会很多，故而使用单向顺序流即可
        paramsMap.forEach((key, value) -> sb.append(key)
                .append(equalSymbol)
                .append(value)
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
        showRequestLog(url, bodyMap, token, HttpMethod.POST);
        HttpEntity<Map<String, Object>> httpEntity = loadHttpEntity(paramMap, token);
        JSONObject responseJson;
        try {
            responseJson = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        } catch (RestClientException e) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, e.getMessage());
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.GLOBAL_MS_MSG);
        }
        if (responseJson == null) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, "响应为null");
            return new ResultBody(new GlobalErrorInfoException(GlobalErrorInfoEnum.GLOBAL_MS_MSG));
        }
        showResponseLog(url, responseJson.toJSONString());
        return convertResponse(responseJson);
    }


    /**
     * POST方式调用，传body对象, 对象为List
     *
     * @param url
     * @param paramList
     * @return
     */
    public ResultBody postListBody(String url, List<?> paramList)
            throws GlobalErrorInfoException {
        List<?> filteredList = paramList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        String token = loadToken();
        Map<String, Object> bodyLoggingMap = new HashMap<String, Object>(4, 0.5f) {
            {
                put("List<?>", paramList);
            }
        };
        showRequestLog(url, bodyLoggingMap, token, HttpMethod.POST);
        HttpEntity<List<?>> httpEntity = loadListHttpEntity(filteredList, token);
        JSONObject responseJson;
        try {
            responseJson = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        } catch (RestClientException e) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, e.getMessage());
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.GLOBAL_MS_MSG);
        }
        if (responseJson == null) {
            log.error("调用微服务接口失败! 接口: {} \n原因: {}", url, "响应为null");
            return new ResultBody(new GlobalErrorInfoException(GlobalErrorInfoEnum.GLOBAL_MS_MSG));
        }
        showResponseLog(url, responseJson.toJSONString());
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
     */
    public void showRequestLog(String url, Map<String, Object> paramMap, String token, HttpMethod httpMethod) {
        // 针对文件上传, 设置不打印LOG
        if (Objects.nonNull(paramMap.get(fileOperateLoggingKey))
                && Boolean.FALSE.toString().equals(paramMap.get(fileOperateLoggingKey))) {
            return;
        }
        log.info("\n[请求微服务接口]:\n"
                + "请求url=" + url + "\n"
                + "请求方式=" + StringUtils.upperCase(httpMethod.name()) + "\n"
                + "请求参数paramsMap=" + JSONUtil.formatStandardJSON(new JSONObject(paramMap).toJSONString()) + "\n"
                + "携带token=" + token
        );
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
     */
    public void showResponseLog(String url, String jsonString) {
        log.info("\n[微服务响应]:\n"
                + "接口url=" + url + "\n"
                + "接口响应:\n" + JSONUtil.formatStandardJSON(jsonString)
        );
    }


}
