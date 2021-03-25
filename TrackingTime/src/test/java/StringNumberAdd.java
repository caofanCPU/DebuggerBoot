public class StringNumberAdd {

    public static int[] add(String numberA, String numberB) {
        int maxLength = Math.max(numberA.length(), numberB.length());
        String source = numberA + numberB;
        char[] charArray = source.toCharArray();
        // 结果, 两数相加结果最多比最大数多一位
        int[] result = new int[maxLength + 1];
        // 来自低位的进位
        int carry = 0;
        int indexB, a, b, sum;
        for (int indexA = maxLength - 1; indexA >= 0; indexA--) {
            // 确定数据B的索引
            indexB = indexA + numberB.length();
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

    public static void main(String[] args) {
        String numberA = "9";
        String numberB = "1";
        int[] result = add(numberA, numberB);
        System.out.println("输入A: " + numberA);
        System.out.println("输入B: " + numberB);
        System.out.print("两数和: ");
        for (int i = 0; i < result.length; i++) {
            if (i == 0 && result[i] == 0) {
                continue;
            }
            System.out.print(result[i]);
        }
        System.out.println();
    }



}
