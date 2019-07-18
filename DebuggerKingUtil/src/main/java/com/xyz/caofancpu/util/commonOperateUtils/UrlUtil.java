package com.xyz.caofancpu.util.commonOperateUtils;

import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Ardy
 */
public class UrlUtil {

    public static final String UTF_TYPE = "utf-8";
    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);

    /**
     * 对URL进行指定格式的转码
     *
     * @param url
     * @param contextType
     * @return
     */
    public static String encodeUrl(String url, final String contextType)
            throws GlobalErrorInfoException {
        if (StringUtils.isNotEmpty(url)) {
            try {
                return URLEncoder.encode(url, contextType);
            } catch (UnsupportedEncodingException e) {
                logger.error("url编码失败, 原因: {}", e);
            }
        }
        throw new GlobalErrorInfoException("URL参数不能为空!");
    }

    /**
     * 对url地址以特定的编码进行解码
     *
     * @param url
     * @param contextType
     * @return
     */
    private static String decodeUrl(String url, final String contextType)
            throws GlobalErrorInfoException {
        if (StringUtils.isNotEmpty(url)) {
            try {
                return URLDecoder.decode(url, contextType);
            } catch (UnsupportedEncodingException e) {
                logger.error("URL解码失败, 原因: {}", e);
            }
        }
        throw new GlobalErrorInfoException("URL参数不能为空!");
    }

    public static void main(String[] args) {
        String originUrl = "http://cwq.nongjicai.com/#/orderConfirm?orderId=10025";
        String encodeUrlByUTF = null;
        try {
            encodeUrlByUTF = encodeUrl(originUrl, UTF_TYPE);
        } catch (GlobalErrorInfoException e) {
            // ignore
        }
        System.out.println(encodeUrlByUTF);
        String decodeUrlByUTF = null;
        try {
            decodeUrlByUTF = decodeUrl(encodeUrlByUTF, UTF_TYPE);
        } catch (GlobalErrorInfoException e) {
            // ignore
        }
        System.out.println(decodeUrlByUTF);
        Assert.isTrue(originUrl.equals(decodeUrlByUTF), "测试失败！");
    }

}
