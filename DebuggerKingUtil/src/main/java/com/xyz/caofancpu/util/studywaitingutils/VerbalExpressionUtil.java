package com.xyz.caofancpu.util.studywaitingutils;

import com.xyz.caofancpu.util.commonoperateutils.NormalUseUtil;
import com.xyz.caofancpu.util.commonoperateutils.SymbolConstantUtil;
import lombok.NonNull;
import ru.lanwen.verbalregex.VerbalExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: VerbalExpressionUtil
 *
 * @author caofanCPU
 */

public class VerbalExpressionUtil {

    public static void main(String[] args)
            throws Exception {
        VerbalExpression regex = VerbalExpression.regex()
                .capt()
                .find("Mo")
                .endCapt()
                .endOfLine()
                .build();
        NormalUseUtil.out(regex.toString());
        NormalUseUtil.out(executePatternRex(regex, "MoCMoMo", SymbolConstantUtil.EMPTY));
    }

    public static String executePatternRex(VerbalExpression regexExpression, String originText, String replacer) {
        Pattern pattern = Pattern.compile(regexExpression.toString());
        Matcher matcher = pattern.matcher(originText);
        return matcher.replaceAll(replacer);
    }

    /**
     * 美化多个换行符
     *
     * @param source
     * @return
     */
    public static String beautyNextLine(@NonNull String source) {
        VerbalExpression regex = VerbalExpression.regex()
                .lineBreak().oneOrMore()
                .build();
        return executePatternRex(regex, source, SymbolConstantUtil.NEXT_LINE);
    }

    /**
     * 清除空白字符
     *
     * @param source
     * @return
     */
    public static String cleanWhiteChar(@NonNull String source) {
        VerbalExpression regex = VerbalExpression.regex()
                .capt()
                .space().oneOrMore()
                .or("\\n").oneOrMore()
                .or("\\r\\n").oneOrMore()
                .or("\\t").oneOrMore()
                .endCapt()
                .build();
        return executePatternRex(regex, source, SymbolConstantUtil.EMPTY);
    }
}
