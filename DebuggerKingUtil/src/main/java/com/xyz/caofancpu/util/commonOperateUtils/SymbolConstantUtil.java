package com.xyz.caofancpu.util.commonOperateUtils;

import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

/**
 * 常用符号
 */
public class SymbolConstantUtil {

    /**
     * 中文顿号
     */
    public static final String CHINESE_STOP = "、";
    /**
     * 英文句号
     */
    public static final String ENGLISH_FULL_STOP = ".";
    /**
     * 中文逗号
     */
    public static final String CHINESE_COMMA = "，";
    /**
     * 英文逗号
     */
    public static final String ENGLISH_COMMA = ",";
    /**
     * 空串
     */
    public static final String EMPTY = StringUtils.EMPTY;
    /**
     * 单个空格符
     */
    public static final String SPACE = StringUtils.SPACE;
    /**
     * EXCEL中汉字的缩进间距，设置为4个空格
     */
    public static final String CHINESE_INDENT = CollectionUtil.join(Collections.nCopies(4, SPACE), EMPTY);
    /**
     * 填空占位符: 两条短横线
     */
    public static final String FILL_EMPTY_PLACE_HOLDER = "--";


}