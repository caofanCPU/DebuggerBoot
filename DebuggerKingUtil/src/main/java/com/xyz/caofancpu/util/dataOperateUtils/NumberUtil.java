package com.xyz.caofancpu.util.dataOperateUtils;

import com.xyz.caofancpu.util.commonOperateUtils.NormalUseUtil;
import com.xyz.caofancpu.util.commonOperateUtils.SymbolConstantUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Created by caofanCPU on 2018/7/3.
 *
 * @author
 */
@Slf4j
public class NumberUtil {

    /**
     * 汉语中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    /**
     * 汉语中货币单位大写，设计类似占位符
     */
    private static final String[] CN_UPPER_MONETRAY_UNIT = {"分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟"};

    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";

    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";

    /**
     * 数字精度，默认值为2
     */
    private static final int DEFAULT_PRECISION = 2;

    /**
     * 数字精度，默认值为2
     */
    private static final int VIRTUAL_COIN_PRECISION = 8;

    /**
     * 100, 百分数分母值
     */
    private static final int ONE_HUNDRED = 100;

    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZERO_FULL = "零元" + CN_FULL;

    /**
     * 获取指定位数的随机数
     *
     * @param digit
     * @return
     */
    public static Integer getInteger(int digit) {
        if (digit >= 32) {
            digit = 31;
        }
        return (int) ((Math.random() * 9 + 1) * Math.pow(10, digit - 1));
    }

    public static String convertToCN(@NonNull BigDecimal numberOfMoney) {
        StringBuilder sb = new StringBuilder();
        int signum = numberOfMoney.signum();
        // 零元整
        if (signum == 0) {
            return CN_ZERO_FULL;
        }
        // 金额的四舍五入
        long number = numberOfMoney.movePointRight(DEFAULT_PRECISION)
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .abs()
                .longValue();
        // 小数点后两位值
        long scale = number % ONE_HUNDRED;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共四种情况: 00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (number > 0) {
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!getZero) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }

    /**
     * 将数字字符串转换为中文大写金额
     *
     * @param str
     * @return
     */
    public static String parseMoneyCN(String str) {
        BigDecimal numberOfMoney = new BigDecimal(str);
        return convertToCN(numberOfMoney);
    }

    /**
     * 虚拟货币对象转BigDecimal, 默认8位小数, 默认采用四舍五入保留2位小数
     *
     * @param price
     * @return
     */
    public static BigDecimal convertVirtualCoinPrice(@NonNull Object price) {
        return convertToBigDecimal(price, VIRTUAL_COIN_PRECISION, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 对象转BigDecimal, 默认采用四舍五入保留2位小数
     *
     * @param source
     * @param newScale
     * @return
     */
    public static BigDecimal convertToBigDecimal(@NonNull Object source, int newScale) {
        if (newScale <= 0) {
            newScale = DEFAULT_PRECISION;
        }
        return convertToBigDecimal(source, newScale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 对象转BigDecimal, 默认采用四舍五入保留2位小数
     *
     * @param source
     * @param newScale
     * @param roundingMode
     * @return
     */
    public static BigDecimal convertToBigDecimal(@NonNull Object source, int newScale, int roundingMode) {
        if (newScale <= 0) {
            newScale = DEFAULT_PRECISION;
        }
        if (roundingMode <= 0) {
            roundingMode = BigDecimal.ROUND_HALF_UP;
        }
        return new BigDecimal(source.toString()).setScale(newScale, roundingMode);
    }

    /**
     * 对象转BigDecimal, 默认采用四舍五入保留2位小数
     *
     * @param source
     * @return
     */
    public static BigDecimal convertToDefaultBigDecimal(@NonNull Object source) {
        return convertToBigDecimal(source, DEFAULT_PRECISION, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算百分比(2位精度), 返回字符串 xx.00%
     *
     * @param value
     * @param referValue
     * @return
     */
    public static String calculateViewPercent(@NonNull Long value, @NonNull Long referValue) {
        BigDecimal x = BigDecimal.valueOf(value);
        BigDecimal y = BigDecimal.valueOf(referValue);
        BigDecimal oneHundred = BigDecimal.valueOf(ONE_HUNDRED);
        return x.divide(y, DEFAULT_PRECISION, BigDecimal.ROUND_HALF_UP).multiply(oneHundred).toString() + SymbolConstantUtil.PERCENT;
    }

    public static void main(String[] args) {
        NormalUseUtil.out(calculateViewPercent(200L, 300L));
        NormalUseUtil.out(parseMoneyCN("123456789.546"));
    }

}
