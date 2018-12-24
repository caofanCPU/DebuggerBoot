package com.xyz.caofancpu.util.dataOperateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by caofanCPU on 2018/7/2.
 */
public class DateUtil {
    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    
    public final static String FORMAT_SIMPLE_DETAIL = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_SIMPLE_DETAIL_PRECISE = "yyyy-MM-dd HH:mm:ss:SSS";
    public final static String FORMAT_SIMPLE = "yyyy-MM-dd";
    public final static String FORMAT_SIMPLE_CN = "yyyy年MM月dd日";
    
    public static String date2StrForSimpleNU(Date date) {
        return date2Str(date, FORMAT_SIMPLE_DETAIL);
    }
    
    public static String date2StrForSimple(Date date) {
        return date2Str(date, FORMAT_SIMPLE);
    }
    
    public static String date2StrForSimpleCN(Date date) {
        return date2Str(date, FORMAT_SIMPLE_CN);
    }
    
    public static String date2StrForSimpleDetail(Date date) {
        return date2Str(date, FORMAT_SIMPLE_DETAIL);
    }
    
    public static String date2StrForSimpleDetailPrecise(Date date) {
        return date2Str(date, FORMAT_SIMPLE_DETAIL_PRECISE);
    }
    
    public static String date2Str(Date date, String format) {
        if (Objects.nonNull(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }
    
    /**
     * 根据起始时间, 计算结束时间
     *
     * @param startDate
     * @param monthNum
     * @param dayNum
     * @return
     */
    public static Date getOverTime(Date startDate, int monthNum, int dayNum) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        //把日期往后增加一年.整数往后推,负数往前移动
        calendar.add(calendar.MONTH, monthNum);
        //把日期往后增加一年.整数往后推,负数往前移动
        calendar.add(calendar.DAY_OF_MONTH, dayNum);
        return calendar.getTime();
    }
}
