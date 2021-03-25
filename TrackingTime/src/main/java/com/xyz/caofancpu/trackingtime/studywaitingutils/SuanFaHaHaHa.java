package com.xyz.caofancpu.trackingtime.studywaitingutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

public class SuanFaHaHaHa {

    public static void main(String[] args) {
//        new 最长回文子串().test();
//        new 两数求和().test();
//        new 最长公共子串("ac", "ab").test();
//        new 最长公共子串("gc", "ab").test();
//        new 最长公共子串("abc", "abcd").test();
//        new 最长公共子串("acdf", "fdca").test();
        new 最长公共子串("acaddd", new StringBuilder("acaddd").reverse().toString()).test();
//        new 最长公共子数组().test();
//        for (int i = 0; i < 5; i++) {
//            new 环球旅行(5, i).test();
//        }
    }

    public static abstract class Base {
        abstract void test();
    }

    /**
     * @see com.xyz.caofancpu.trackingtime.studywaitingutils.SuanFaHaHaHa.最长公共子串
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 最长公共子数组 extends Base {
        private int[] arrA = new int[]{1, 3, 4, 6, 1, 2};
        private int[] arrB = new int[]{4, 6, 1, 2};

        @Override
        void test() {
            int[] lcs = lcs(arrA, arrB);
            for (int e : lcs) {
                System.out.print(e + "  ");
            }
        }

        public int[] lcs(int[] shortA, int[] longB) {
            int[] none = {};
            if (shortA == null || shortA.length == 0 || longB == null || longB.length == 0) {
                return none;
            }
            if (shortA.length > longB.length) {
                return lcs(longB, shortA);
            }
            int repeatNum = 0;
            int lastIndex = 0;
            int[][] table = new int[shortA.length][longB.length];
            for (int i = 0; i < shortA.length; i++) {
                for (int j = 0; j < longB.length; j++) {
                    if (shortA[i] != longB[j]) {
                        table[i][j] = 0;
                    } else {
                        if (i == 0 || j == 0) {
                            table[i][j] = 1;
                        } else {
                            table[i][j] = table[i - 1][j - 1] + 1;
                        }
                    }
                    if (repeatNum < table[i][j]) {
                        repeatNum = table[i][j];
                        lastIndex = i;
                    }
                }
            }
            viewTable(shortA, longB, table);
            if (repeatNum == 0 || lastIndex + 1 < repeatNum) {
                return none;
            }
            int[] result = new int[repeatNum];
            for (int i = 0; i < repeatNum; i++) {
                result[i] = shortA[lastIndex + 1 - repeatNum + i];
            }
            return result;
        }

        /**
         * 打印二维匹配矩阵表
         */
        private void viewTable(int[] shortA, int[] longB, int[][] table) {
            //    s h o r t A
            // l  row1
            // o  row2
            // n  row3
            // g  row4
            // B  row5
            String separator = "  ";
            System.out.print(" " + separator);
            for (int i : longB) {
                System.out.print(i + separator);
            }
            System.out.println();
            int[] arrA = shortA;
            for (int i = 0; i < table.length; i++) {
                System.out.print(arrA[i] + separator);
                for (int j = 0; j < table[i].length; j++) {
                    System.out.print(table[i][j] + separator);
                }
                System.out.println();
            }
        }
    }

    /**
     * 基本思路: 两个字符串构建二维矩阵, 相同的置为1, 不同的置为0
     * 优化: 两个字符串长短不一, 公共子串肯定不超过较短的字符串, 以它为外循环, 依次匹配长字符串
     * .    字符串为空情况处理, 长串包含子串特殊情况
     * 要点: 尽量构建横向(行少列多)二维矩阵时, 这样比较符合视觉
     * .    因此: 二维表table[m][n], m是行数, n是列数, m应<=n
     * .    即: m对应短串, n对应长串
     * 重要代码点: 借助两个变量, 重复字符数repeatNum, 最后相等字符的短串最长索引lastIndex
     * .         匹配时字符不等, table[i][j]都为0
     * .         只要匹配时字符相等, table[i][j]初始化要么为1, 要么左上角字符匹配结果+1: table[i-1][j-1]
     * .         最后一起判断如果重复字符数repeatNum小于table[i][j], 那么更改repeatNum和lastIndex
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 最长公共子串 extends Base {
        private String sourceA = "abcd";
        private String sourceB = "abc";

        @Override
        void test() {
            String repeatChars = lcs(sourceA, sourceB);
            System.out.println(repeatChars);
        }

        /**
         * 最长公共子串, 构造二维表, 长>=宽, 短串匹配长串
         * .   a  b  c  d
         * .a  1  0  0  0
         * .b  0  2  0  0
         * .c  0  0  3  0
         */
        public String lcs(String shortA, String longB) {
            String none = "无公共子串";
            if (shortA == null || shortA.length() == 0 || longB == null || longB.length() == 0) {
                return none;
            }
            if (shortA.length() > longB.length()) {
                return lcs(longB, shortA);
            }
            if (longB.contains(shortA)) {
                return shortA;
            }
            // 重复字符数量
            int repeatNum = 0;
            // 最后一个重复字符的索引
            int lastIndex = 0;
            int[][] table = new int[shortA.length()][longB.length()];
            for (int i = 0; i < shortA.length(); i++) {
                for (int j = 0; j < longB.length(); j++) {
                    if (shortA.charAt(i) != longB.charAt(j)) {
                        table[i][j] = 0;
                    } else {
                        if (i == 0 || j == 0) {
                            table[i][j] = 1;
                        } else {
                            table[i][j] = table[i - 1][j - 1] + 1;
                        }
                    }
                    if (repeatNum < table[i][j]) {
                        repeatNum = table[i][j];
                        lastIndex = i;
                    }
                }
            }
            viewTable(shortA, longB, table);
            return repeatNum == 0 || (lastIndex + 1 < repeatNum) ? none : shortA.substring(lastIndex + 1 - repeatNum, lastIndex + 1);
        }

        /**
         * 打印二维匹配矩阵表
         */
        private void viewTable(String shortA, String longB, int[][] table) {
            //    s h o r t A
            // l  row1
            // o  row2
            // n  row3
            // g  row4
            // B  row5
            String separator = "  ";
            System.out.print(" " + separator);
            for (char c : longB.toCharArray()) {
                System.out.print(c + separator);
            }
            System.out.println();
            char[] charA = shortA.toCharArray();
            for (int i = 0; i < table.length; i++) {
                System.out.print(charA[i] + separator);
                for (int j = 0; j < table[i].length; j++) {
                    System.out.print(table[i][j] + separator);
                }
                System.out.println();
            }
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 环球旅行 extends Base {
        private int circleN = 10;
        private int stepK = 3;

        @Override
        void test() {
            int caseNum = calculateSteps(this.circleN, this.stepK);
            System.out.println(caseNum);
        }

        //n为环数，k为步数
        public int calculateSteps(int n, int k) {
            if (n == 0) {
                return 1;
            }
            //如果只有2个环，则偶数有1个方法，奇数不能到达
            if (n == 2) {
                if (k % 2 == 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
            int[][] dp = new int[k + 1][n];
            //0步到达0点有1种方法
            dp[0][0] = 1;
            //0步到达任意点有0种方法,可java自赋0值，可省略
            //for(int i=0;i<n;i++){
            //    dp[0][i]=0
            //}
            for (int j = 1; j <= k; j++) {
                for (int i = 0; i < n; i++) {
                    //j步达到i点的问题，转化为j-1步从相邻的两个节点到达目标节点的方法数之和。
                    //需要保证在0~n-1范围，故需要防止越界进行处理
                    dp[j][i] = dp[j - 1][(i - 1 + n) % n] + dp[j - 1][(i + 1) % n];
                }
            }
            //这里的0对应的是回到0点，达到任意点可以通过将0改为目标点即可
            return dp[k][0];
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 两数求和 extends Base {
        private String numberA = "9";
        private String numberB = "10";

        @Override
        void test() {
            int[] result = add(this.numberA, this.numberB);
            System.out.println("输入A: " + this.numberA);
            System.out.println("输入B: " + this.numberB);
            System.out.print("两数和: ");
            for (int i = 0; i < result.length; i++) {
                if (i == 0 && result[i] == 0) {
                    continue;
                }
                System.out.print(result[i]);
            }
            System.out.println();
        }

        public int[] add(String longA, String shortB) {
            if (longA.length() < shortB.length()) {
                return add(shortB, longA);
            }
            int maxLength = longA.length();
            String source = longA + shortB;
            char[] charArray = source.toCharArray();
            // 结果, 两数相加结果最多比最大数多一位
            int[] result = new int[maxLength + 1];
            // 来自低位的进位
            int carry = 0;
            int indexB, a, b, sum;
            for (int indexA = maxLength - 1; indexA >= 0; indexA--) {
                // 确定数据B的索引
                indexB = indexA + shortB.length();
                // 数据A的数值
                a = charArray[indexA] - 48;
                // 数据B的数值
                b = indexB < maxLength ? 0 : charArray[indexB] - 48;
                // A+B并加上来自低位的进位
                sum = a + b + carry;
                // 确定进位
                carry = sum / 10;
                // 结果存入数组
                result[indexA + 1] = sum % 10;
            }
            if (carry != 0) {
                // 边界条件
                result[0] = carry;
            }
            return result;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 最长回文子串 extends Base {
        private String source = "abcbadddddddddd";

        @Override
        void test() {
            int maxLcpsLength = maxLcpsLength(this.source);
            System.out.println("输入字符串: " + this.source + "; 最大回文串长度为: " + maxLcpsLength);
        }

        // 处理字符串
        public char[] manacherString(String str) {
            char[] chars = str.toCharArray();
            char[] res = new char[chars.length * 2 + 1];
            int index = 0;
            for (int i = 0; i < res.length; i++)
                // 偶数位加@, 奇数位不变
                res[i] = (i & 1) == 0 ? '@' : chars[index++];
            return res;
        }

        // 马拉车算法
        public int maxLcpsLength(String str) {
            char[] chars = manacherString(str);
            int[] len = new int[chars.length]; // 记录每个回文字符串的长度
            int right = -1; // 当前回文的右边界
            int cen = -1; // 当前回文的中心
            int max = -1; // 记录回文的最大值
            for (int i = 0; i < chars.length; i++) {
                // 2*cen-i是i点关于cen的对称点
                // right>i时, Math.min(len[2*cen-i], right-i), 两种情况取最小, 往外扩
                // right<=i时, 以i为中心的回文没有被访问过, 所以当前回文字符串只有i, len[i]=1
                len[i] = right > i ? Math.min(len[2 * cen - i], right - i) : 1;

                // 以i为中心, 左右扩
                while (i - len[i] > -1 && i + len[i] < chars.length) {
                    if (chars[i - len[i]] == chars[i + len[i]]) {
                        len[i]++; // 左右字符相等, 符合回文, len++
                    } else {
                        break;
                    }
                }
                // 更新当前回文的右边界以及中心
                if (i + len[i] > right) {
                    right = i + len[i];
                    cen = i;
                }
                max = Math.max(max, len[i]);
            }
            return max - 1;
        }
    }


}
