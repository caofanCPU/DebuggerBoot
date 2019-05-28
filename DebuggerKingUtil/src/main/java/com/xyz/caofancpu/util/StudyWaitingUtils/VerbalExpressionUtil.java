package com.xyz.caofancpu.util.StudyWaitingUtils;

import ru.lanwen.verbalregex.VerbalExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: VerbalExpressionUtil
 *
 * @author: caofanCPU
 * @date: 2019/1/28 19:10
 */

public class VerbalExpressionUtil {
    public static void main(String[] args) throws Exception {
    
    }
    
    public static String executePatternRex(VerbalExpression regexExpression, String originText, String replacer) {
        Pattern pattern = Pattern.compile(regexExpression.toString());
        Matcher matcher = pattern.matcher(originText);
        return matcher.replaceAll(replacer);
    }
}
