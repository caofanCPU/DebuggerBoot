package com.xyz.caofancpu.util.StudyWaitingUtils;

import org.apache.commons.lang3.StringUtils;
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
    public static void main(String[] args) {
        VerbalExpression testRegex = VerbalExpression.regex()
                .startOfLine().find("0").oneOrMore()
                .build();
        String testString = "0000000010000000000000000";
        System.out.println(testRegex.toString());
        System.out.println(executePatternRex(testRegex, testString, StringUtils.SPACE));
    }
    
    public static String executePatternRex(VerbalExpression regexExpression, String originText, String replacer) {
        Pattern pattern = Pattern.compile(regexExpression.toString());
        Matcher matcher = pattern.matcher(originText);
        return matcher.replaceAll(replacer);
    }
}
