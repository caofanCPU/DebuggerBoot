package com.xyz.caofancpu;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xyz.caofancpu.util.commonOperateUtils.NormalUseUtil;
import com.xyz.caofancpu.util.commonOperateUtils.SymbolConstantUtil;
import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 策略规则测试
 */
public class StrategyRuleTest {

    public static void main(String[] args) {
//        bindingAndExclusiveTest();
        minAndMaxSelectTest();
    }

    /**
     * 计算最少最多选择关系
     * 思路:
     * Required:[A₁]
     * Strategy:[A₁,B,C,D,E], min=3, max=4
     * RealStrategy=Strategy-Required=[B,C,D,E]
     * RealMin=min-Required.size=2
     * RealMax=max-Required.size=3
     * if, Choose:[A₁,B,C]
     * - RealChoose=Choose-Required=[B,C]
     * - JudgeChoose=RealChoose ∩ RealStrategy=[B,C], 非空==>匹配当前规则, 需要用当前策略进行约束校验
     * - JudgeChoose=[B,C], RealStrategy=[B,C,D,E], RealMin=2, RealMax=3
     * - ∵ RealMin <= JudgeChoose.size <= RealMax
     * - ∴ 符合该规则, 继续进行其他规则的匹配与约束校验
     * <p>
     * if, Choose:[A₁,B,C,D,E]
     * - RealChoose=Choose-Required=[B,C,D,E]
     * - JudgeChoose=RealChoose ∩ RealStrategy=[B,C,D,E], 非空==>匹配当前规则, 需要用当前策略进行约束校验
     * - JudgeChoose=[B,C,D,E], RealStrategy=[B,C,D,E], RealMin=2, RealMax=3
     * - ∵ JudgeChoose.size > RealMax
     * - ∴ 不符合该规则, 判定失败: "[B,C,D,E]最多选RealMax个"
     * <p>
     * if, Choose:[A₁]
     * - RealChoose=Choose-Required=[]
     * - JudgeChoose=RealChoose ∩ RealStrategy=[], 为空==>不匹配当前规则, continue
     * <p>
     * if, Choose:[A₁, B]
     * - RealChoose=Choose-Required=[B]
     * - JudgeChoose=RealChoose ∩ RealStrategy=[B], 非空==>匹配当前规则, continue
     * - JudgeChoose=[B], RealStrategy=[B,C,D,E], RealMin=2, RealMax=3
     * - ∵ JudgeChoose.size < RealMin
     * - ∴ 不符合该规则, 判定失败: "[B,C,D,E]最少选RealMin个"
     * 示例:
     * 商品列表: [A, B, C, D, E, F, G, H], 必选[A]
     * 配置策略:
     * 1. [A, B],      min=1, max=2
     * 2. [A, C, D]    min=1, max=2
     * 3. [A, E, F, G] min=1, max=2
     * 选择商品(×代表不符合规则, √代表满足规则, ≠代表和规则无关):
     * []              ×, "选择商品不能为空"
     * [A]             √, √规则1, √规则2, √规则3
     * [A,B]           √, √规则1, ≠规则2, ≠规则3
     * [A,C]           √, ≠规则1, √规则2, ≠规则3
     * [A,D]           √, ≠规则1, √规则2, ≠规则3
     * [A,B,C]         √, √规则1, √规则2, ≠规则3
     * [A,B,D]         √, √规则1, √规则2, ≠规则3
     * [A,C,D]         ×, ≠规则1, ×规则2, ≠规则3, "商品C, 商品D, 最多选1个"
     * [A,G]           √, ≠规则1, ≠规则2, √规则3
     * [A,E,G]         ×, ≠规则1, ≠规则2, ×规则3, "商品E, 商品F, 商品G最多选1个"
     * [A,C,E,F]       ×, ≠规则1, √规则2, ×规则3, "商品E, 商品F, 商品G最多选1个"
     */
    private static void minAndMaxSelectTest() {
        List<String> requiredNameList = loadRequiredNameList();
        Map<Integer, Strategy> strategyMap = loadStrategy();
        loadChooseNameList().forEach(list -> {
            List<String> realChoose = CollectionUtil.subtract(ArrayList::new, list, requiredNameList);
            strategyMap.forEach((id, strategy) -> {
                List<String> configNameList = strategy.getConfigNameList();
                List<String> realStrategy = CollectionUtil.subtract(ArrayList::new, configNameList, requiredNameList);
                List<String> intersection = CollectionUtil.intersection(ArrayList::new, realChoose, realStrategy);
                if (CollectionUtil.isEmpty(intersection)) {
                    // 为空, 跳过
                    NormalUseUtil.out("实选[" + CollectionUtil.join(realChoose, SymbolConstantUtil.ENGLISH_COMMA) + "]与策略[" + CollectionUtil.join(realStrategy, SymbolConstantUtil.ENGLISH_COMMA) + "]无交集, 忽略");
                    return;
                }
                int min = strategy.getMin() - requiredNameList.size();
                int max = strategy.getMax() - requiredNameList.size();
                if (intersection.size() >= min && intersection.size() <= max) {
                    // 符合校验, 跳过
                    NormalUseUtil.out("实选[" + CollectionUtil.join(realChoose, SymbolConstantUtil.ENGLISH_COMMA) + "]符合策略[" + CollectionUtil.join(realStrategy, SymbolConstantUtil.ENGLISH_COMMA) + "]");
                    return;
                }
                if (intersection.size() < min) {
                    NormalUseUtil.out("实选[" + CollectionUtil.join(realChoose, SymbolConstantUtil.ENGLISH_COMMA) + "]不符合策略[" + CollectionUtil.join(realStrategy, SymbolConstantUtil.ENGLISH_COMMA) + "], 原因: 至少选" + min + "个");
                }
                if (intersection.size() > max) {
                    NormalUseUtil.out("实选[" + CollectionUtil.join(realChoose, SymbolConstantUtil.ENGLISH_COMMA) + "]不符合策略[" + CollectionUtil.join(realStrategy, SymbolConstantUtil.ENGLISH_COMMA) + "], 原因: 至多选" + max + "个");
                }
            });
        });
    }

    private static List<List<String>> loadChooseNameList() {
        List<String> choose1 = Lists.newArrayList(loadRequiredNameList());
        List<String> choose2 = Lists.newArrayList(loadRequiredNameList());
        choose2.addAll(Lists.newArrayList("B"));
        List<String> choose3 = Lists.newArrayList(loadRequiredNameList());
        choose3.addAll(Lists.newArrayList("B", "C"));
        List<String> choose4 = Lists.newArrayList(loadRequiredNameList());
        choose4.addAll(Lists.newArrayList("B", "C", "D", "E"));
        List<String> choose5 = Lists.newArrayList(loadRequiredNameList());
        choose5.addAll(Lists.newArrayList("F"));

        List<String> choose6 = Lists.newArrayList(loadRequiredNameList());
        choose6.addAll(Lists.newArrayList("G", "H"));
        List<String> choose7 = Lists.newArrayList(loadRequiredNameList());
        choose7.addAll(Lists.newArrayList("B", "C", "G", "H", "F"));

        List<List<String>> testList = new ArrayList<>();
        testList.add(choose1);
        testList.add(choose2);
        testList.add(choose3);
        testList.add(choose4);
        testList.add(choose5);
        testList.add(choose6);
        testList.add(choose7);
        return testList;
    }

    private static Map<Integer, Strategy> loadStrategy() {
        Map<Integer, Strategy> strategyMap = Maps.newHashMap();
        StrategyRuleTest.Strategy strategy1 = new StrategyRuleTest.Strategy();
        List<String> configNameList1 = Lists.newArrayList(loadRequiredNameList());
        configNameList1.addAll(Lists.newArrayList("B", "C", "D", "E"));
        strategy1.setConfigNameList(configNameList1).setMin(3).setMax(4);
        strategyMap.put(1, strategy1);

        StrategyRuleTest.Strategy strategy2 = new StrategyRuleTest.Strategy();
        List<String> configNameList2 = Lists.newArrayList(loadRequiredNameList());
        configNameList2.addAll(Lists.newArrayList("G", "H"));
        strategy2.setConfigNameList(configNameList2).setMin(1).setMax(2);
        strategyMap.put(2, strategy2);

        return strategyMap;
    }

    private static List<String> loadAllNameList() {
        return Lists.newArrayList("A₁", "B", "C", "D", "E", "F", "G", "H");
    }

    private static List<String> loadRequiredNameList() {
        return Lists.newArrayList("A₁");
    }

    private static void bindingAndExclusiveTest() {
        Map<Pair<String, String>, Boolean> relationshipMap = loadRelationshipMap();
        List<String> positiveSelectedList = Lists.newArrayList("A", "M");
        // 根据关系分组
        Map<Boolean, List<Pair<String, String>>> bindingOrExclusiveAsKeyMap = CollectionUtil.groupIndexToMap(relationshipMap.entrySet(), Map.Entry::getValue, Map.Entry::getKey);

        Map<String, List<Pair<String, String>>> bindingMap = CollectionUtil.groupIndexToMap(bindingOrExclusiveAsKeyMap.get(Boolean.TRUE), Pair::getLeft);
        Map<String, List<Pair<String, String>>> exclusiveMap = CollectionUtil.groupIndexToMap(bindingOrExclusiveAsKeyMap.get(Boolean.FALSE), Pair::getLeft);
        List<String> bindingResultList = recursiveSearchRelationship(positiveSelectedList, bindingMap);
        List<String> exclusiveRelationshipResultList = recursiveSearchRelationship(positiveSelectedList, exclusiveMap);

        List<String> exclusiveResultList = CollectionUtil.subtract(ArrayList::new, exclusiveRelationshipResultList, positiveSelectedList);
        NormalUseUtil.out("enabled:");
        bindingResultList.forEach(NormalUseUtil::out);
        NormalUseUtil.out("disabled:");
        exclusiveResultList.forEach(NormalUseUtil::out);
    }

    private static List<String> recursiveSearchRelationship(List<String> keyList, Map<String, List<Pair<String, String>>> relationMap) {
        List<String> resultList = Lists.newArrayList();
        for (String key : keyList) {
            resultList.add(key);
            if (CollectionUtil.isNotEmpty(relationMap.get(key))) {
                List<String> bindingKeyList = CollectionUtil.transToList(relationMap.get(key), Pair::getRight);
                resultList.addAll(recursiveSearchRelationship(bindingKeyList, relationMap));
            }
        }
        // 递归结束条件
        return resultList;
    }

    /**
     * A->B->C->D, A->Q, A-x-F, F-x-G, G-x-H
     * M->N->O->P, M-x-X, X-x-Y, Y-x-Z
     * <p>
     * if i input [A, M], the result should like this:
     * enabled:   [A, B, C, D, Q, M, N, O, P]
     * disabled:  [F, G, H, X, Y, Z]
     *
     * @return
     */
    private static Map<Pair<String, String>, Boolean> loadRelationshipMap() {
        Map<Pair<String, String>, Boolean> dataMap = Maps.newHashMap();
        dataMap.put(Pair.of("A", "B"), Boolean.TRUE);
        dataMap.put(Pair.of("A", "Q"), Boolean.TRUE);
        dataMap.put(Pair.of("B", "C"), Boolean.TRUE);
        dataMap.put(Pair.of("C", "D"), Boolean.TRUE);
        dataMap.put(Pair.of("M", "N"), Boolean.TRUE);
        dataMap.put(Pair.of("N", "O"), Boolean.TRUE);
        dataMap.put(Pair.of("O", "P"), Boolean.TRUE);
        dataMap.put(Pair.of("A", "F"), Boolean.FALSE);
        dataMap.put(Pair.of("F", "G"), Boolean.FALSE);
        dataMap.put(Pair.of("G", "H"), Boolean.FALSE);
        dataMap.put(Pair.of("M", "X"), Boolean.FALSE);
        dataMap.put(Pair.of("X", "Y"), Boolean.FALSE);
        dataMap.put(Pair.of("Y", "Z"), Boolean.FALSE);
        return dataMap;
    }

    @Data
    @Accessors(chain = true)
    public static class Strategy {
        private List<String> configNameList = Lists.newArrayList();
        private Integer min;
        private Integer max;
    }

}
