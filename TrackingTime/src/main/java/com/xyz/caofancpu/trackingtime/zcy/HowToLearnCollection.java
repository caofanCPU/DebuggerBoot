package com.xyz.caofancpu.trackingtime.zcy;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.util.commonOperateUtils.NormalUseUtil;
import com.xyz.caofancpu.util.dataOperateUtils.NumberUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集合学习
 * 1.描述问题
 * 2.构建已知数据
 * 3.操作集合: 收集|取用|分组|排序|筛选
 * 4.基础写法 VS 工具方法
 *
 * @author caofanCPU
 */
public class HowToLearnCollection {

    public static void main(String[] args) {
        printZeroZeroZeroToNineNineNine();

    }


    /**
     * 按照顺序打印000-999
     * <p>
     * 1.已知{@link NormalUseUtil#out(String)}可以打印字符串
     * 使用"" + 数字可以构造出一个字符串参数
     * 2.允许的操作, 只能用循环及1中提供的打印方法完成
     * 3.求: 按照顺序打印000-999
     * <p>
     * 训练点: 循环操作
     *
     * @author ZCY
     */
    public static void printZeroZeroZeroToNineNineNine() {
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    NormalUseUtil.out("" + i + j + k);
                }
            }
        }

    }


    /**
     * 借助随机数构造000-999数字集合
     * <p>
     * 1.已知{@link #randomNumBetweenZeroAndNine}可以随机产生一位数字, 其值范围为0-9,
     * {@link com.xyz.caofancpu.util.commonOperateUtils.NormalUseUtil#out(String)}可以打印字符串
     * 2.允许的操作: 借助{@link #randomNumBetweenZeroAndNine}方法拼接出3位数, 借助循环收集3位数结果, 借助循环打印3位数结果
     * 3.求: 按照000, 001, 002,..., 999顺序打印结果
     * <p>
     * 训练点: 循环操作|List收集元素|List取用元素
     *
     * @return
     */
    public static List<List<Integer>> howToProduceZeroZeroZeroToNineNineNine() {
        List<List<Integer>> resultList = new ArrayList<>(1000);
        // TODO: write your code below


        return resultList;
    }


    /**
     * 统计循环次数
     * <p>
     * 1.已知问题见{@link #howToProduceZeroZeroZeroToNineNineNine}
     * 2.现在想知道, 当000-999数字全部构造完成时，统计方法{@link #randomNumBetweenZeroAndNine}被使用的平均次数
     * 例如: 第一次用了10万次, 第二次用了20万次, 第三次用了30万次, 求三次平均结果就是20万次
     * 3.求: 输出每一次试验的次数列表, 以及最终的平均结果
     * <p>
     * 训练点: 循环操作|List收集元素|List取用元素
     */
    public static void countHowManyTimesToCompleteProduce() {
        // TODO: write your code below

    }

    /**
     * 1.已知问题见{@link #howToProduceZeroZeroZeroToNineNineNine}
     * 2.对于1中的结果, 现在需要计算这三位数代表的数值, 将结果存入另一个List<Integer>中
     * 3.这一步好像很无聊, 但是为了简化后续的问题, 需要完成这一步为后续问题提供中间结果
     * <p>
     * 训练点: 循环操作|List收集元素|List取用元素|克服无聊心态->任务拆分->将长代码分割为多段代码
     */
    public static List<Integer> calculateValue() {
        List<Integer> resultList = Lists.newArrayList();
        // TODO: write your code below

        return resultList;
    }

    /**
     * 对一个整数集合, 根据奇数偶数进行分组归并
     * <p>
     * 1.已知问题见{@link #calculateValue}
     * 2.对于1中计算出0-999的数值结果, 将这1000个数字按照奇数偶数分组归类
     * 3.输出结果, 输出格式:
     * 奇数:
     * 1,3,...999
     * 偶数:
     * 0,2,...998
     * <p>
     * 训练点: 循环操作|Map收集元素|Map取用元素|将List转为Map
     *
     * @return
     */
    public static Map<Boolean, List<Integer>> groupByIsEven() {
        Map<Boolean, List<Integer>> resultMap = new HashMap<>();
        // TODO: write your code below

        return resultMap;
    }

    /**
     * 随机产生一个一位数字0-9
     *
     * @return
     */
    private static Integer randomNumBetweenZeroAndNine() {
        return NumberUtil.getInteger(1);
    }

}
