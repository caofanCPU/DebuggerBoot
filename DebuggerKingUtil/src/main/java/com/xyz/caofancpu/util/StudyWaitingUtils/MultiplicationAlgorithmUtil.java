package com.xyz.caofancpu.util.StudyWaitingUtils;

import org.apache.commons.lang3.StringUtils;
import ru.lanwen.verbalregex.VerbalExpression;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * FileName: MultiplicationAlgorithmUtil
 */

public class MultiplicationAlgorithmUtil {
    /**
     * 字串递归结束条件: 规模只要在这个范围内可以直接计算（整型数值满足）
     */
    public final static int THRESHOLD_LENGTH = 2;

    /**
     * 0字串用于填充高位
     */
    public final static String ZERO = "0";

    /**
     * 递归次数标识
     */
    public static int recursionNum;

    /**
     * 大数字串乘法
     *
     * @param X
     * @param Y
     * @return
     */
    public static String bigIntMultiply(String X, String Y) {
        int maxLength = Math.max(X.length(), Y.length());

        // 递归结束点: 达到阈值条件进行整数乘法运算
        if (maxLength <= THRESHOLD_LENGTH) {
            return String.valueOf(Integer.parseInt(X) * Integer.parseInt(Y));
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

        out("==========第" + (recursionNum++) + "次递归=============");
        // 从低位往高位方向逐一计算结果
        // 先计算BD的进位及留位
        String[] sBD = handleString(BD, B.length());
        // 再计算 AD + BC
        String ADBC = add(AD, BC);
        // BD若有进位, 则需要加到 AD + BC中
        if (!ZERO.equals(sBD[1])) {
            ADBC = add(ADBC, sBD[1]);
        }
        // 计算AD + BC 的进位
        String[] sADBC = handleString(ADBC, Math.max(A.length(), B.length()));

        // 最后计算AC + [(AD + BC)的进位]
        AC = add(AC, sADBC[1]);
        // 拼接结果 AC-(AD + BC)-BD
        return AC + sADBC[0] + sBD[0];
    }

    /**
     * 正则剔除字串高位上的 0
     *
     * @param text
     * @return
     */
    public static String removeRareZero(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        VerbalExpression removeZeroFirstRegex = VerbalExpression.regex()
                .startOfLine().find(ZERO).oneOrMore()
                .build();
        return VerbalExpressionUtil.executePatternRex(removeZeroFirstRegex, text, StringUtils.EMPTY);
    }

    /**
     * 两个大数字串的加法
     * 注意: 字符串 + 运算底层就是新建StringBuilder, 每次 + 都新建SB且会复制数组元素
     * sb.insert(0, value)可以把value添加在头部, 但是确实通过value.append(sb.toString()的复制)完成的, 效率待考量
     *
     * @param AD
     * @param BC
     * @return
     */
    public static String add(String AD, String BC) {
        int maxLength = Math.max(AD.length(), BC.length());
        // 返回的结果, 放入StringBuilder中, 并指定初始容量, 避免扩容
        StringBuilder sb = new StringBuilder(maxLength + 16);
        // 高位补零
        if (AD.length() != BC.length()) {
            if (AD.length() > BC.length()) {
                BC = fillZero(BC, maxLength);
            } else {
                AD = fillZero(AD, maxLength);
            }
        }
        // 按位加，进位存储在flag中
        int carry = 0;
        // 按序从后往前按位求和
        for (int i = maxLength - 1; i >= 0; i--) {
            int currentValue = carry + Integer.parseInt(AD.substring(i, i + 1))
                    + Integer.parseInt(BC.substring(i, i + 1));
            // 计算进位和留位
            carry = currentValue / 10;
            int remainValue = currentValue % 10;

            // 拼接结果字符串, 高位需要添加到头部
            sb.insert(0, remainValue);
        }
        if (carry != 0) {
            sb.insert(0, carry);
        }
        // 先逆序再输出
        String result = sb.toString();
        out("处理加法操作数字串, 字串AD=[" + AD + "], 字串BC=[" + BC + "], 加法结果=[" + result + "]");
        return result;
    }

    /**
     * 处理数字串, 分离出进位与留位
     * 返回的数组第一个为留位数字串, 第二个为进位数字串
     *
     * @param multiResult
     * @param referredLength
     * @return
     */
    public static String[] handleString(String multiResult, int referredLength) {
        String[] handleResult = new String[]{multiResult, ZERO};

        if (multiResult.length() > referredLength) {
            int upgradeLength = multiResult.length() - referredLength;
            handleResult[0] = multiResult.substring(upgradeLength);
            handleResult[1] = multiResult.substring(0, upgradeLength);
        } else {
            // 位数不足则填充零
            handleResult[0] = fillZero(handleResult[0], referredLength);
        }
        out("处理数字串=[" + multiResult + "], 参考长度=[" + referredLength + "], " +
                "处理结果: 进位=[" + handleResult[1] + "], 留位=[" + handleResult[0] + "]");
        return handleResult;
    }

    /**
     * 格式化处理数字字符串，高位补零
     *
     * @param X
     * @param maxLength
     * @return
     */
    public static String fillZero(String X, int maxLength) {
        // StringBuilder初始化容量
        StringBuilder sb = new StringBuilder(maxLength);
        sb.append(X);
        while (sb.length() < maxLength) {
            sb.insert(0, ZERO);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Pattern numberPattern = getPattern("^[1-9]\\d*$");
        while (true) {
            initRecursionNum();
            System.out.println("请输入乘数A（不以0开头的正整数）：");
            Scanner sc = new Scanner(System.in);
            String A = sc.next();
            if (checkNumber(numberPattern, A)) {
                System.out.println("乘数A输入不合法, 请检查！");
                continue;
            }

            System.out.println("请输入被乘数B（不以0开头的正整数）：");
            String B = sc.next();
            if (checkNumber(numberPattern, B)) {
                System.out.println("被乘数B输入不合法, 请检查！");
                continue;
            }
            long startTime = System.currentTimeMillis();
            String result = bigIntMultiply(A, B);
            long endTime = System.currentTimeMillis();
            result = removeRareZero(result);
            out("一共递归(" + recursionNum + ")次, 耗费(" + (endTime - startTime) + ")ms完成计算: [乘数A] * [被乘数B] = [结果]");
            out(A + " * " + B + " = " + result);
            out("===========================================");
        }
    }

    public static void initRecursionNum() {
        recursionNum = 1;
    }

    public static Pattern getPattern(String rule) {
        return Pattern.compile(rule);
    }

    public static boolean checkNumber(Pattern numberPattern, String value) {
        return Objects.isNull(numberPattern) || !numberPattern.matcher(value).matches();
    }

    public static void out(String text) {
        System.out.println(text);
    }


    // TODO: Function for finding sum of larger numbers
    public static String findSum(String str1, String str2) {
        // Before proceeding further, make sure length
        // of str2 is larger.
        if (str1.length() > str2.length()) {
            String t = str1;
            str1 = str2;
            str2 = t;
        }

        // Take an empty String for storing result
        StringBuilder str = new StringBuilder();

        // Calculate length of both String
        int n1 = str1.length(), n2 = str2.length();
        int diff = n2 - n1;

        // Initially take carry zero
        int carry = 0;

        // Traverse from end of both Strings
        for (int i = n1 - 1; i >= 0; i--) {
            // Do school mathematics, compute sum of
            // current digits and carry
            int sum = ((str1.charAt(i) - '0') + (str2.charAt(i + diff) - '0') + carry);
            str.append((char) (sum % 10 + '0'));
            carry = sum / 10;
        }

        // Add remaining digits of str2[]
        for (int i = n2 - n1 - 1; i >= 0; i--) {
            int sum = ((str2.charAt(i) - '0') + carry);
            str.append((char) (sum % 10 + '0'));
            carry = sum / 10;
        }

        // Add remaining carry
        if (carry > 0)
            str.append((char) (carry + '0'));

        // reverse resultant String
        return new StringBuilder(str.toString()).reverse().toString();
    }

}
