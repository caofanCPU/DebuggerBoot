package com.xyz.caofancpu.util.studywaitingutils;

import com.xyz.caofancpu.util.commonoperateutils.NormalUseUtil;
import com.xyz.caofancpu.util.commonoperateutils.SymbolConstantUtil;
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
}
