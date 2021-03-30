package com.xyz.caofancpu.trackingtime.studywaitingutils;

import java.util.Random;

/**
 *
 */
public class NASA {
    public static void main(String[] args) {
//        edgeValueTest();
//        randomValueTest();
        System.out.println(Integer.MIN_VALUE);
    }

    /**
     * 基本思路: 一个合法的数值字符串, 除第一个字符可以是 数字||'+'||'-' 外, 其余字符都必须为数字,
     * .       所以考虑遍历字符串, 对第一个字符做限定判断: 不满足 数字||'+'||'-' 就直接返回
     * .       对于合法的数字字符, 可以使用数字字符的十进制数值×权值, 权值与字符索引的关系式: Math(10, 字符长度-1-索引)
     * .       考虑边界条件: 当字符数组足够长时, 其数值范围就超过int数值范围了, [-2147483648, 2147483647]
     * .                   假定输入数据在long范围内, 用long型存储求和结果
     * .                   边界条件: -2147483648, -2147483649, 2147483647, 2147483648
     * 优化: 数字0在任何位置不影响求和结果, 故计算条件为 '0'<字符<='9'
     * 时间复杂度: 一次循环, O(n)
     * 空间复杂度: 符号标志位和求和两个变量, O(1)
     * TODO: 能否去掉输入数据在long范围内的假设, 在一次循环内, 既能完成求和又能做出正确的边界判断?
     *
     * @param source
     * @return
     */
    public static String doConvert(String source) {
        final String ILLEGAL_RESULT = "-1";
        if (source == null || source.isEmpty()) {
            return ILLEGAL_RESULT;
        }
        char[] chars = source.toCharArray();
        // 默认正数
        boolean isPositive = true;
        long result = 0L;
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                if (chars[0] == '-') {
                    isPositive = false;
                    continue;
                } else if (chars[0] == '+') {
                    continue;
                } else if (chars[0] < '0' || chars[0] > '9') {
                    return ILLEGAL_RESULT;
                }
            }
            if (chars[i] > '0' && chars[i] <= '9') {
                // 合法字符, 123数值为1*10^(3-1-0) + 2*10^(3-1-1) + 3*10^(3-1-2)
                result = result + (chars[i] - '0') * (long) Math.pow(10, chars.length - 1 - i);
                continue;
            }
            // 皆为非法字符
            return ILLEGAL_RESULT;
        }
        if (isPositive) {
            return "" + Math.min(result, Integer.MAX_VALUE);
        }
        return "-" + Math.min(result, (long) Integer.MAX_VALUE + 1);
    }

    /**
     * 随机值测试
     */
    private static void randomValueTest() {
        for (int i = 0; i < 1000; i++) {
            String s = random(i);
            System.out.println("输入: " + s + ",\t输出: " + doConvert(s));
        }
    }

    /**
     * 边界值测试
     */
    private static void edgeValueTest() {
        String[] edgeValueTest = new String[]{
                "" + Integer.MAX_VALUE,
                "+" + Integer.MAX_VALUE,
                "" + ((long) (Integer.MAX_VALUE) + 1),
                "+" + ((long) (Integer.MAX_VALUE) + 1),
                "" + Integer.MIN_VALUE,
                "" + ((long) (Integer.MIN_VALUE) - 1),
                // 非法值1
                "-" + Integer.MIN_VALUE,
                // 非法值2
                "-" + ((long) (Integer.MIN_VALUE) - 1),
        };
        for (String s : edgeValueTest) {
            System.out.println("输入: " + s + ",\t输出: " + doConvert(s));
        }
    }

    /**
     * 随机值测试
     *
     * @param length
     * @return
     */
    private static String random(int length) {
        char[] chars = new char[]{
                '+', '-',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '@', '#', '&', '^', '<', '>', '?', ':', '[', ']'
        };
        length = Math.min(length, chars.length / 2);
        StringBuilder source = new StringBuilder(length);
        Random indexGenerator = new Random();
        for (int i = 0; i < length - 1; i++) {
            source.append(chars[indexGenerator.nextInt(length)]);
        }
        return source.toString();
    }

}
