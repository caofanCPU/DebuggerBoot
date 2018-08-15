package com.xyz.caofancpu.utils;

import com.bynz.utils.common.Assert;
import org.apache.commons.lang3.StringUtils;

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
    public static String encodeUrl(String url, final String contextType) {
        if (StringUtils.isNotEmpty(url)) {
            try {
                url = URLEncoder.encode(url, contextType);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                url = "";
            }
        }
        return url;
    }
    
    /**
     * 对url地址以特定的编码进行解码
     *
     * @param url
     * @param contextType
     * @return
     */
    private static String decodeUrl(String url, final String contextType) {
        if (StringUtils.isNotEmpty(url)) {
            try {
                url = URLDecoder.decode(url, contextType);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            url = "";
        }
        return url;
    }
    
    public static void main(String[] args) {
        String originUrl = "http://cwq.nongjicai.com/#/orderConfirm?orderId=10025";
        String encodeUrlByUTF = encodeUrl(originUrl, UTF_TYPE);
        System.out.println(encodeUrlByUTF);
        String decodeUrlByUTF = decodeUrl(encodeUrlByUTF, UTF_TYPE);
        System.out.println(decodeUrlByUTF);
        Assert.isTrue(originUrl.equals(decodeUrlByUTF));
    }
    
}
