package com.xyz.caofancpu.util.dataoperateutils;

import com.xyz.caofancpu.util.commonoperateutils.NormalUseUtil;
import com.xyz.caofancpu.util.commonoperateutils.SymbolConstantUtil;
import com.xyz.caofancpu.util.streamoperateutils.CollectionUtil;
import com.xyz.caofancpu.util.studywaitingutils.VerbalExpressionUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * FileName: StringAlignUtil
 * Author:   caofanCPU
 * Date:     2018/11/15 17:00
 */
@Slf4j
public class StringAlignUtil {
    public static final Pattern SQL_COLUMN_WHITE_CHAR = Pattern.compile("((?:\\s)+)");

    public static void main(String[] args) {
        String sqlText = "app_name,\n" +
                "live_watch_pv,\n" +
                "live_watch_uv,\n" +
                "valid_live_watch_pv,\n" +
                "valid_live_watch_uv,\n" +
                "live_watch_fans_pv,\n" +
                "live_watch_fans_uv,\n" +
                "live_watch_not_fans_pv,\n" +
                "live_watch_not_fans_uv,\n" +
                "valid_live_watch_fans_pv,\n" +
                "valid_live_watch_fans_uv,\n" +
                "valid_live_watch_not_fans_pv,\n" +
                "valid_live_watch_not_fans_uv,\n" +
                "watch_pv_entrance_show,\n" +
                "watch_uv_entrance_show,\n" +
                "fans_watch_pv_entrance_show,\n" +
                "fans_watch_uv_entrance_show,\n" +
                "not_fans_watch_pv_entrance_show,\n" +
                "not_fans_watch_uv_entrance_show,\n" +
                "click_pv_entrance,\n" +
                "click_uv_entrance,\n" +
                "fans_click_pv_entrance,\n" +
                "fans_click_uv_entrance,\n" +
                "not_fans_click_pv_entrance,\n" +
                "not_fans_click_uv_entrance";
        NormalUseUtil.out(formatSQLColumn(sqlText, "sum(", ")", true));
    }

    /**
     * Format SQL columns, for example, add function or rename by using 'AS'
     *
     * @param originText
     * @param prefix
     * @param suffix
     * @return
     */
    public static String formatSQLColumn(@NonNull String originText, @NonNull String prefix, @NonNull String suffix, boolean... clearUnderLine) {
        String legalText = SQL_COLUMN_WHITE_CHAR.matcher(originText.replaceAll(SymbolConstantUtil.CHINESE_COMMA, SymbolConstantUtil.ENGLISH_COMMA)).replaceAll(SymbolConstantUtil.EMPTY);
        String splitSymbol = SymbolConstantUtil.ENGLISH_COMMA;
        List<String> stringList = CollectionUtil.splitDelimitedStringToList(legalText, splitSymbol, String::toString);
        List<String> completeFixList = CollectionUtil.transToList(stringList, item -> prefix + item + suffix);
        int singleLineMaxChars = CollectionUtil.max(completeFixList, String::length).intValue();
        Alignment formatAlignment = Alignment.LEFT;
        List<String> formattedLineList = formatSQLColumn(singleLineMaxChars, formatAlignment, completeFixList);
        if (Objects.nonNull(clearUnderLine) && clearUnderLine[0]) {
            stringList = CollectionUtil.transToList(stringList, StringAlignUtil::cleanUnderLineForSQLAliasName);
        }
        Map<Integer, String> indexMap = CollectionUtil.transToMap(stringList, stringList::indexOf, Function.identity());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < formattedLineList.size(); i++) {
            String column = indexMap.get(i);
            result.append(formattedLineList.get(i))
                    .append(SymbolConstantUtil.TAB).append("AS").append(SymbolConstantUtil.SPACE).append(SymbolConstantUtil.SPACE).append(column)
                    .append(SymbolConstantUtil.ENGLISH_COMMA).append(SymbolConstantUtil.NEXT_LINE);
        }
        return StringUtils.isEmpty(result) ? result.toString() : result.deleteCharAt(result.lastIndexOf(SymbolConstantUtil.ENGLISH_COMMA)).toString();
    }

    public static String cleanUnderLineForSQLAliasName(@NonNull String columnName) {
        String result = columnName;
        for (int i = 0; i < 4; i++) {
            if (VerbalExpressionUtil.CAMEL_UNDERLINE_2_NO_UNDERLINE_UNCAPITALIZE.matcher(result).matches()) {
                break;
            }
            result = VerbalExpressionUtil.camelUnderLineNameConverter(result);
        }
        return result;
    }

    /**
     * Format by splitSymbol, such as ',' or NEXT_LINE
     *
     * @param originText
     * @param splitSymbol
     * @param currentAlignment
     * @return
     */
    public static String formatBySplitSymbol(String originText, String splitSymbol, Alignment currentAlignment) {
        List<String> stringList = CollectionUtil.splitDelimitedStringToList(originText, splitSymbol, String::toString);
        return formatMultiLine(stringList, currentAlignment);
    }

    /**
     * MultiLine format
     *
     * @param stringList
     * @param currentAlignment
     * @return
     */
    public static String formatMultiLine(List<String> stringList, Alignment currentAlignment) {
        int singleLineMaxChars = CollectionUtil.max(stringList, String::length).intValue();
        return format(singleLineMaxChars, currentAlignment, stringList);
    }

    /**
     * Format multi-string
     *
     * @param singleLineMaxChars
     * @param currentAlignment
     * @param stringList
     * @return
     */
    public static String format(int singleLineMaxChars, Alignment currentAlignment, List<String> stringList) {
        checkAlignmentParam(singleLineMaxChars, currentAlignment);
        StringBuilder result = new StringBuilder();
        for (String wanted : stringList) {
            switch (currentAlignment) {
                case RIGHT:
                    pad(result, singleLineMaxChars - wanted.length());
                    result.append(wanted);
                    break;
                case CENTER:
                    int toAdd = singleLineMaxChars - wanted.length();
                    pad(result, toAdd / 2);
                    result.append(wanted);
                    pad(result, toAdd - toAdd / 2);
                    break;
                case LEFT:
                    result.append(wanted);
                    pad(result, singleLineMaxChars - wanted.length());
                    break;
            }
            result.append(SymbolConstantUtil.NEXT_LINE);
        }
        return result.toString();
    }

    /**
     * Format sql columns, return multi-lines
     *
     * @param stringList
     * @param singleLineMaxChars
     * @param currentAlignment
     * @return
     */
    public static List<String> formatSQLColumn(int singleLineMaxChars, Alignment currentAlignment, List<String> stringList) {
        String result = format(singleLineMaxChars, currentAlignment, stringList);
        return CollectionUtil.splitDelimitedStringToList(result, SymbolConstantUtil.NEXT_LINE, String::toString);
    }

    /**
     * Supplementary space
     *
     * @param to
     * @param howMany
     */
    public static void pad(StringBuilder to, int howMany) {
        for (int i = 0; i < howMany; i++) {
            to.append(SymbolConstantUtil.SPACE);
        }
    }

    /**
     * Split text, especially is paragraph
     *
     * @param text
     * @param singleLineMaxChars
     * @return
     */
    public static List<String> splitInputText(String text, int singleLineMaxChars) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(text)) {
            return list;
        }
        if (singleLineMaxChars < 0) {
            throw new IllegalArgumentException("singleLineMaxChars must be positive.");
        }
        for (int i = 0; i < text.length(); i = i + singleLineMaxChars) {
            list.add(text.substring(i, Math.min(i + singleLineMaxChars, text.length())));
        }
        return list;
    }

    /**
     * Basic check
     *
     * @param singleLineMaxChars
     * @param align
     */
    public static void checkAlignmentParam(int singleLineMaxChars, Alignment align) {
        if (singleLineMaxChars < 0) {
            throw new IllegalArgumentException("singleLineMaxChars must be positive.");
        }
        if (align != Alignment.LEFT && align != Alignment.CENTER && align != Alignment.RIGHT) {
            throw new IllegalArgumentException("invalid justification arg.");
        }
    }

    /**
     * Format paragraph
     *
     * @param text
     * @param singleLineMaxChars
     * @param currentAlignment
     * @return
     */
    public String formatText(String text, int singleLineMaxChars, Alignment currentAlignment) {
        List<String> stringList = splitInputText(text, singleLineMaxChars);
        return format(singleLineMaxChars, currentAlignment, stringList);
    }

    public enum Alignment {
        LEFT,
        CENTER,
        RIGHT,
        ;
    }
}
