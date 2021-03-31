package com.xyz.caofancpu.trackingtime.studywaitingutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Random;

public class SuanFaHaHaHa {

    public static void main(String[] args) {
//        new 最长回文子串().test();
//        new 最长公共子串("ac", "ab").test();
//        new 最长公共子串("gc", "ab").test();
//        new 最长公共子串("abc", "abcd").test();
//        new 最长公共子串("acdf", "fdca").test();
//        new 最长公共子串("acaddd", new StringBuilder("acaddd").reverse().toString()).test();
//        new 最长公共子数组().test();
//        new 跳台阶(30).test();

//        new 环球旅行(3, 3).test();
//        System.out.println();
//        new 平衡二叉树().test();
//
//        new 环球旅行(2, 2).test();
//        System.out.println();
//
//        new 环球旅行(2, 1).test();
//        System.out.println();
        bitSkill();
    }

    public static void bitSkill() {
        System.out.println("1.异或^         : a^b = b^a");
        System.out.println("2.异或^         : a^a = 0; a^0 = a; a^1 = a的反码 ==> 应用: 将一个变量快速清零: a = a^a");
        System.out.println("3.异或^         : a^b^b = a; a^b^a = b ==> 应用: 快速交换两个整数: int x = x^y; int y = x^y; int x = x^y;");
        System.out.println("4.与&           : ==> 应用: 快速判断奇数偶数: a&1 == 0 ? \"偶数\" : \"奇数\"");
        System.out.println("5.异或^和右移>>  : ==> 应用: 快速求绝对值: int i = a >> 31; int abs = (a^i) - i; 注意小于或等于Integer.MIN_VALUE求绝对值会溢出得到错误结果");
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

    /**
     * 基本思路: 边界条件, n=0, k=0
     * .  k步代表0~k, 所以构建二维矩阵表时table[k+1][n]
     * .  行为步数step, 列为节点索引index
     * .  初始条件: table[0][0]=1
     * .  动态规划函数式: table[step][i]=左斜上角+右斜上角
     * .  考虑到索引边界问题: table[step][i]也需要=左上角+右上角
     * .  公式: table[step][i] = table[step-1][(i-1+n)%n] + table[step-1][(i+1+n)%n]
     * .  得到的二维表能代表从【0】出发经过【k】步到达节点【n】的所有情况
     * 优化: 处理特殊情况, 尽早返回
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 环球旅行 extends Base {
        /**
         * 点的个数
         */
        private int circleN = 10;
        /**
         * 允许使用步数
         */
        private int stepK = 3;

        @Override
        void test() {
            int[][] dp = calculateSteps(this.stepK, this.circleN);
            viewTable(dp);
        }

        public int[][] calculateSteps(int k, int n) {
            int[][] dp = new int[k + 1][n];
            // 特殊情况可快速处理
//            if (n == 0) {
//                return dp;
//            }
//            if (n == 2) {
//                if ((k & 1) == 0) {
//                    dp[k-1][0] = 1;
//                }
//                return dp;
//            }
            // 初始值
            dp[0][0] = 1;
            for (int step = 1; step <= k; step++) {
                for (int index = 0; index < n; index++) {
                    dp[step][index] = dp[step - 1][(index - 1 + n) % n] + dp[step - 1][(index + 1) % n];
                    System.out.println("(" + step + ", " + index + ")=(" + (step - 1) + ", " + ((index - 1 + n) % n) + ") + (" + (step - 1) + ", " + ((index + 1 + n) % n) + ")");
                }
            }
            return dp;
        }

        /**
         * 打印二维匹配矩阵表
         * k步作为Row, 节点n作为列
         */
        private void viewTable(int[][] table) {
            String separator = "  ";
            System.out.print("Match[x]" + separator);
            for (int i = 0; i < table[0].length; i++) {
                System.out.print("Index[" + i + "]" + separator);
            }
            System.out.println();
            for (int step = 0; step < table.length; step++) {
                System.out.print(".Step[" + step + "]" + separator);
                for (int index = 0; index < table[step].length; index++) {
                    System.out.print(separator + separator + separator + table[step][index] + " " + separator);
                }
                System.out.println();
            }
        }
    }

    /**
     * 基本思路: 区分短串和长串, 以长串为标准进行一次循环, 循环中与短串求和并确定进位、结果值
     * 优化: 将短串拼接到长串后面, 长串长度即为分界点, 循环长串时可以计算到短串索引, 不存在的则为0
     * 要点: 两数相加结果比长串可能多一位
     * .    indexA = maxLength - 1, 长串倒着遍历
     * .    indexB = indexA + shortB.length(), 短串也倒着遍历, 索引推导
     */
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
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class 整数解析 extends Base {

        @Override
        void test() {
            edgeValueTest();
            randomValueTest();
        }

        /**
         * @param source
         * @return
         * @see Integer#valueOf(String)
         */
        public String integerValueOf(String source) {
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
                        if (chars.length == 1) {
                            return ILLEGAL_RESULT;
                        }
                        isPositive = false;
                        continue;
                    } else if (chars[0] == '+') {
                        if (chars.length == 1) {
                            return ILLEGAL_RESULT;
                        }
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
                if (chars[i] == '0') {
                    // 字符'0'不参与计算, 但是是合法字符
                    continue;
                }
                // 其他皆为非法字符
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
        private void randomValueTest() {
            for (int i = 0; i < 1000; i++) {
                String s = random(i);
                System.out.println("输入: " + s + ",\t输出: " + integerValueOf(s));
            }
        }

        /**
         * 边界值测试
         */
        private void edgeValueTest() {
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
                    "+",
                    "-",
                    "0",
                    "9",
                    "000",
                    "001"
            };
            for (String s : edgeValueTest) {
                System.out.println("输入: " + s + ",\t输出: " + integerValueOf(s));
            }
        }

        /**
         * 随机值测试
         *
         * @param length
         * @return
         */
        private String random(int length) {
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

    /**
     * 基本思路: 将原字符串翻转, 利用最长公共子串解决
     * TODO: 优化: Manacher算法, 时间复杂度缩减到n, 减少不必要的判断和计算
     */
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

    /**
     * 基本思路: 边界值, n=0, n=1时, 进而发现是递归调用, 再使用迭代改进递归
     * .  相当于应用数学归纳法
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 跳台阶 extends Base {
        private int n = 3;

        @Override
        void test() {
            System.out.println(_jump1Or2(this.n));
            System.out.println(jump1Or2(this.n));
            System.out.println(_jumpN(this.n));
            System.out.println(_jumpNOptimized(this.n));
        }

        /**
         * 递归
         * 1.边界条件确定可举例的初始值
         * 2.递归函数式
         * 3.首尾思考, n
         *
         * @param value
         */
        @Deprecated
        public int _jump1Or2(int value) {
            if (value <= 2) {
                return value;
            }
            return _jump1Or2(value - 1) + _jump1Or2(value - 2);
        }

        public int jump1Or2(int value) {
            if (value <= 2) {
                return value;
            }
            int a1 = 1;
            int a2 = 2;
            int result = 0;
            for (int i = 3; i <= value; i++) {
                result = a1 + a2;
                // 迭代推进
                a1 = a2;
                a2 = result;
            }
            return result;
        }

        @Deprecated
        public int _jumpN(int value) {
//            System.out.println(value + ", ");
            if (value <= 1) {
                return 1;
            }
            int result = 0;
            for (int i = value; i >= 1; i--) {
                result += _jumpN(i - 1);
            }
            return result;
        }

        @Deprecated
        public int _jumpNOptimized(int value) {
            // f(n)=f(n-1)+f(n-2)+...+f(2)+f(1)
            // f(n-1)=     f(n-2)+...+f(2)+f(1)
            // f(n) - f(n-1) = f(n-1)
            // f(n) = 2 * f(n-1)
            // cause: f(1)=1=2^0, f(2)=2=2^1
            // so: f(n)=2^(n-1)
            return (int) Math.pow(2, value - 1);
        }

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 二叉树最大路径和 extends Base {
        private int a = 1;

        @Override
        void test() {

        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class 平衡二叉树 extends Base {
        private boolean result;
        private TreeNode<Integer> root;
        private int height = 1;

        public int getDepth(TreeNode<Integer> root) {
            if (root == null) {
                result = true;
                return 0;
            }
            // 左子树
            int left = getDepth(root.left);
            // 右子树
            int right = getDepth(root.right);
            int depth = (Math.max(left, right)) + 1;
            result = Math.abs(left - right) <= 1;
            // 下层的深度, 上层可以接着用免得再遍历
            return depth;
        }

        @Override
        void test() {
            buildTree(true);
            height = getDepth(root);
            System.out.println("高度: " + height + ", 平衡: " + result);
            buildTree(false);
            height = getDepth(root);
            System.out.println("高度: " + height + ", 平衡: " + result);
        }

        private void buildTree(boolean balance) {
            root = new TreeNode<>(1);
            TreeNode<Integer> node2 = new TreeNode<>(2);
            TreeNode<Integer> node3 = new TreeNode<>(3);
            TreeNode<Integer> node4 = new TreeNode<>(4);
            if (balance) {
                root.setLeft(node2).setRight(node3);
                node3.setRight(node4);
            } else {
                root.setLeft(node2);
                node2.setLeft(node3);
                node3.setRight(node4);
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class TreeNode<T> {
        private T value;
        private TreeNode<T> left;
        private TreeNode<T> right;

        public TreeNode(T value) {
            this.value = value;
        }
    }

}
