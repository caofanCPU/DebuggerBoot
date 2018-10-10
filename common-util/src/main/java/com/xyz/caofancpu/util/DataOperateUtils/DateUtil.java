package com.xyz.caofancpu.util.DataOperateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by caofanCPU on 2018/7/2.
 */
public class DateUtil {
    
    public final static String FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_SIMPLE = "yyyy-MM-dd";
    public final static String FORMAT_SIMPLE_CN = "yyyy年MM月dd日";
    
    
    public static String date2StrForSimpleNU(Date date) {
        return date2Str(date, FORMAT_ALL);
    }
    
    public static String date2StrForSimple(Date date) {
        return date2Str(date, FORMAT_SIMPLE);
    }
    
    public static String date2StrForSimpleCN(Date date) {
        return date2Str(date, FORMAT_SIMPLE_CN);
    }
    
    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }
}
