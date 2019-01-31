package com.xyz.caofancpu.utils;

import ru.lanwen.verbalregex.VerbalExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: VerbalExpressionTestUtil
 *
 * @author: caofanCPU
 * @date: 2019/1/28 19:10
 */

public class VerbalExpressionTestUtil {
    public static void main(String[] args) {
        VerbalExpression testRegex = VerbalExpression.regex()
                .startOfLine().then(",")
                .anything()
                .endOfLine().then("=null")
                .build();
        String testString = "a=null, b='null', c=1, d=\"CF\"";
        System.out.println(testRegex.toString());
        System.out.println(testPatternRex(testRegex.toString(), testString, "OKHTTP!"));
    }
    
    public static String testPatternRex(String regex, String context, String target) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(context);
        return matcher.replaceAll(target);
    }
    
}
