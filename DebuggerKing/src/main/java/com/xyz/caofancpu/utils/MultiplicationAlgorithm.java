package com.xyz.caofancpu.utils;

import org.apache.commons.lang3.StringUtils;
import ru.lanwen.verbalregex.VerbalExpression;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * FileName: MultiplicationAlgorithm
 *
 * @author: caofanCPU
 * @date: 2019/2/28 14:24
 */

public class MultiplicationAlgorithm {
    // 规模只要在这个范围内可以直接计算（整型数值满足）
    public final static int THRESHOLD_LENGTH = 4;
    
    // 其中，len为X、Y的长度最大值
    public static String bigIntMultiply(String X, String Y) {
        int maxLength = Math.max(X.length(), Y.length());
        
        // 递归结束点: 达到阈值条件进行整数乘法运算
        if (maxLength <= THRESHOLD_LENGTH) {
            return "" + (Integer.parseInt(X) * Integer.parseInt(Y));
        }
        
        // 高位补零
        if (X.length() != Y.length()) {
            if (X.length() > Y.length()) {
                Y = fillZero(Y, maxLength);
            } else {
                X = fillZero(X, maxLength);
            }
        }
        
        // 将X、Y分别对半分成两部分
        int midRoller = maxLength >> 1;
        String A = X.substring(0, midRoller);
        String B = X.substring(midRoller);
        String C = Y.substring(0, midRoller);
        String D = Y.substring(midRoller);
        
        // 乘法法则，递归分块处理
        String AC = bigIntMultiply(A, C);
        String AD = bigIntMultiply(A, D);
        String BC = bigIntMultiply(B, C);
        String BD = bigIntMultiply(B, D);
        
        // 注意处理进位的方法，巧妙地运用了字符串的拼接方面
        // 【1】 处理BD，得到原位及进位
        String[] sBD = dealString(BD, B.length());
        // 【2】 处理AD + BC的和
        String ADBC = add(AD, BC);
        // 【3】 加上BD的进位
        if (!"0".equals(sBD[1])) {
            ADBC = add(ADBC, sBD[1]);
        }
        // 【4】 得到ADBC的进位
        String[] sADBC = dealString(ADBC, Math.max(A.length(), B.length()));
        
        // 【5】 AC加上ADBC的进位
        AC = add(AC, sADBC[1]);
        // 【6】 最终结果
        return AC + sADBC[0] + sBD[0];
    }
    
    public static String removeRareZero(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        VerbalExpression removeZeroFirstRegex = VerbalExpression.regex()
                .startOfLine().find("0").oneOrMore()
                .build();
        return VerbalExpressionUtil.executePatternRex(removeZeroFirstRegex, text, StringUtils.SPACE);
    }
    
    // 两个数字串按位加
    public static String add(String AD, String BC) {
        // 返回的结果
        String str = "";
        
        int maxLength = Math.max(AD.length(), BC.length());
        // 高位补零
        if (AD.length() != BC.length()) {
            if (AD.length() > BC.length()) {
                BC = fillZero(BC, maxLength);
            } else {
                AD = fillZero(AD, maxLength);
            }
        }
        
        // 按位加，进位存储在flag中
        int flag = 0;
        // 按序从后往前按位求和
        for (int i = maxLength - 1; i >= 0; i--) {
            int t = flag + Integer.parseInt(AD.substring(i, i + 1))
                    + Integer.parseInt(BC.substring(i, i + 1));
            // 结果超过9，则进位当前位，保留个位数
            if (t > 9) {
                flag = 1;
                t = t - 10;
            } else {
                flag = 0;
            }
            // 拼接结果字符串
            str = "" + t + str;
        }
        if (flag != 0) {
            str = "" + flag + str;
        }
        return str;
    }
    
    // 处理数字串，分离出进位,String数组第一个为原位数字，第二个为进位
    public static String[] dealString(String multiResult, int referredLength) {
        String[] handleResult = new String[]{multiResult, "0"};
        
        if (multiResult.length() > referredLength) {
            int upgradeLength = multiResult.length() - referredLength;
            handleResult[0] = multiResult.substring(upgradeLength);
            handleResult[1] = multiResult.substring(0, upgradeLength);
        } else {
            // 位数不足则填充零
            handleResult[0] = fillZero(handleResult[0], referredLength);
        }
        return handleResult;
    }
    
    // 格式化操作的数字字符串，高位补零
    public static String fillZero(String X, int maxLength) {
        while (maxLength > X.length()) {
            X = "0" + X;
        }
        return X;
    }
    
    public static void main(String[] args) {
        Pattern numberPattern = getPattern("^[1-9]\\d*$");
        while (Boolean.TRUE) {
            System.out.println("请输入乘数A（不以0开头的正整数）：");
            Scanner sc = new Scanner(System.in);
            String A = sc.next();
            if (!checkNumber(numberPattern, A)) {
                System.out.println("乘数A输入不合法, 请检查！");
                sc = null;
                continue;
            }
            
            System.out.println("请输入被乘数B（不以0开头的正整数）：");
            String B = sc.next();
            if (!checkNumber(numberPattern, B)) {
                System.out.println("被乘数B输入不合法, 请检查！");
                sc = null;
                continue;
            }
            
            String result = bigIntMultiply(A, B);
            result = removeRareZero(result);
            out("乘数A * 被乘数B = 结果");
            out(A + " * " + B + " = " + result);
        }
        
    }
    
    
    public static Pattern getPattern(String rule) {
        return Pattern.compile(rule);
    }
    
    public static boolean checkNumber(Pattern numberPattern, String value) {
        return Objects.isNull(numberPattern) ? Boolean.FALSE : numberPattern.matcher(value).matches();
    }
    
    public static void out(String text) {
        System.out.println(text);
    }
    
}
