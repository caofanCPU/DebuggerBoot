package com.xyz.caofancpu.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Ardy
 * @date 2015年5月18日 上午11:47:09
 */
public class UrlUtil {
    
    public static final String UTF_TYPE = "utf-8";
    
    public static final String ISO_TYPE = "ISO-8859-1";
    
    /**
     * 对URL进行指定格式的转码
     *
     * @param url
     * @param contextType
     * @return
     */
    public static String encodeUrl(String url, final String contextType)
            throws UnsupportedEncodingException {
        if (StringUtils.isNotEmpty(url)) {
            return URLEncoder.encode(url, contextType);
        }
        throw new IllegalArgumentException("非法的URL参数:Empty");
    }
    
    /**
     * 对url地址以特定的编码进行解码
     *
     * @param url
     * @param contextType
     * @return
     */
    private static String decodeUrl(String url, final String contextType)
            throws UnsupportedEncodingException {
        if (StringUtils.isNotEmpty(url)) {
            return URLDecoder.decode(url, contextType);
        }
        throw new IllegalArgumentException("非法的URL参数:Empty");
    }
    
    public static void main(String[] args) {
        String originUrl = "http://cwq.nongjicai.com/#/orderConfirm?orderId=10025";
        String encodeUrlByUTF = null;
        try {
            encodeUrlByUTF = encodeUrl(originUrl, UTF_TYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(encodeUrlByUTF);
        String decodeUrlByUTF = null;
        try {
            decodeUrlByUTF = decodeUrl(encodeUrlByUTF, UTF_TYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(decodeUrlByUTF);
        Assert.isTrue(originUrl.equals(decodeUrlByUTF), "测试失败！");
    }
    
}
