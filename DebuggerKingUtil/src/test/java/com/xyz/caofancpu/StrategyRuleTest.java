package com.xyz.caofancpu;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xyz.caofancpu.util.commonOperateUtils.NormalUseUtil;
import com.xyz.caofancpu.util.commonOperateUtils.SymbolConstantUtil;
import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

/**
 * 策略规则测试
 */
public class StrategyRuleTest {

    public static void main(String[] args) {
//        bindingAndExclusiveTest();
//        minAndMaxSelectTest();
//        powerTest();
//        priceSumTest();
        tarjanAlgorithm();
    }

    public static void powerTest() {
        List<Integer> list1 = Lists.newArrayList(1, 2, 3);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        for (int i = 6; i < 101; i++) {
            list.add(i);
        }
        Integer sum = 0;
        for (Integer i : list) {
            sum = sum + i;
        }
        NormalUseUtil.out("" + sum);

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
        Set<String> positiveSelectedSet = Sets.newHashSet("F", "B", "G", "A");
        // 根据关系分组
        Map<Boolean, List<Pair<String, String>>> bindingOrExclusiveAsKeyMap = CollectionUtil.groupIndexToMap(relationshipMap.entrySet(), Map.Entry::getValue, Map.Entry::getKey);
        Map<Boolean, List<String>> resultRelationshipMap = groupBindingAndExclusiveRelationship(bindingOrExclusiveAsKeyMap, Lists.newArrayList(positiveSelectedSet));
        List<String> exclusiveResultList = resultRelationshipMap.get(Boolean.TRUE);
        List<String> bindingResultList = resultRelationshipMap.get(Boolean.FALSE);

        NormalUseUtil.out("positiveSelect:");
        positiveSelectedSet.forEach(NormalUseUtil::out);
        NormalUseUtil.out("binding:");
        bindingResultList.forEach(NormalUseUtil::out);
        NormalUseUtil.out("exclusive:");
        exclusiveResultList.forEach(NormalUseUtil::out);
    }

    /**
     * 根据绑定互斥关系, 以及选定的参考元素RSet, 计算绑定关系列表 + 排斥关系列表
     * 思路:
     * 1.选定参考元素RSet所绑定的元素BSet, 是要被绑定的, 其并集 RSet + BSet = bindingResultSet
     * 2.绑定元素并集bindingResultSet所排斥的元素, 记为一级排斥元素ESet
     * 3.一级排斥元素ESet所绑定的元素, 记作二级排斥元素EBSet, 也是要被排斥的
     *
     * @param bindingOrExclusiveAsKeyMap
     * @param calculateReferElementList
     * @param <T>
     * @return
     */
    public static <T> Map<Boolean, List<T>> groupBindingAndExclusiveRelationship(Map<Boolean, List<Pair<T, T>>> bindingOrExclusiveAsKeyMap, List<T> calculateReferElementList) {
        // 对于绑定|互斥关系, 再次分组归并, 将一个元素key的所有绑定|互斥关系汇聚在value中
        Map<T, List<Pair<T, T>>> bindingMap = CollectionUtil.groupIndexToMap(bindingOrExclusiveAsKeyMap.get(Boolean.TRUE), Pair::getLeft);
        Map<T, List<Pair<T, T>>> exclusiveMap = CollectionUtil.groupIndexToMap(bindingOrExclusiveAsKeyMap.get(Boolean.FALSE), Pair::getLeft);
        List<T> bindingResultList = recursiveSearchRelationship(calculateReferElementList, bindingMap);
        // 计算排斥: 所有绑定的元素所排斥的都是要排斥的
        Set<T> exclusiveCalculateReferElements = CollectionUtil.union(HashSet::new, calculateReferElementList, bindingResultList);
        // 寻找一级排斥, 一级排斥绑定的所有都是二级排斥
        Set<T> firstLevelExclusiveResults = Sets.newHashSet();
        exclusiveCalculateReferElements.forEach(key -> {
            if (CollectionUtil.isNotEmpty(exclusiveMap.get(key))) {
                firstLevelExclusiveResults.addAll(CollectionUtil.transToList(exclusiveMap.get(key), Pair::getRight));
            }
        });
        List<T> exclusiveResultList = recursiveSearchRelationship(Lists.newArrayList(firstLevelExclusiveResults), bindingMap);

        // 如果输入本身有矛盾, 那么该矛盾元素既在绑定结果中又在排斥结果中
        Set<T> contradictionElementSet = CollectionUtil.intersection(HashSet::new, bindingResultList, exclusiveResultList);
        if (CollectionUtil.isNotEmpty(contradictionElementSet)) {
            throw new IllegalArgumentException("非法操作, 以下不可选: " + CollectionUtil.join(contradictionElementSet, SymbolConstantUtil.ENGLISH_COMMA));
        }
        Map<Boolean, List<T>> resultMap = new HashMap<>(4, 0.75f);
        resultMap.put(Boolean.TRUE, bindingResultList);
        resultMap.put(Boolean.FALSE, exclusiveResultList);
        return resultMap;
    }

    private static <T> List<T> recursiveSearchRelationship(List<T> keyList, Map<T, List<Pair<T, T>>> relationMap) {
        List<T> resultList = Lists.newArrayList();
        for (T key : keyList) {
            resultList.add(key);
            if (CollectionUtil.isNotEmpty(relationMap.get(key))) {
                List<T> valueList = CollectionUtil.transToList(relationMap.get(key), Pair::getRight);
                resultList.addAll(recursiveSearchRelationship(valueList, relationMap));
            }
        }
        // 递归结束条件
        return resultList;
    }

    /**
     * A->B->C->D, A->Q, A-x-F, F-x-G, G-x-H
     * M->N->O->P, M-x-X, X-x-Y, Y-x-Z
     * <p>
     * if i input [A, M], the resultIndexList should like this:
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

    /**
     * 连环绑定
     * ↓↽↓↽↑
     * A↣B↣C↣D⇢B, 环状结构: B↣C、C↣D、B↣C↣D
     * 连环排斥
     * ↓↽↽↽↑
     * A×M×N⇢A,    环状结构: A×M×N
     *
     * @return
     */
    private static Map<Pair<String, String>, Boolean> loadChainRelationshipMap() {
        Map<Pair<String, String>, Boolean> dataMap = Maps.newHashMap();
        dataMap.put(Pair.of("A", "B"), Boolean.TRUE);
        dataMap.put(Pair.of("B", "C"), Boolean.TRUE);
        dataMap.put(Pair.of("C", "B"), Boolean.TRUE);
        dataMap.put(Pair.of("C", "D"), Boolean.TRUE);
        dataMap.put(Pair.of("D", "B"), Boolean.TRUE);
        dataMap.put(Pair.of("D", "C"), Boolean.TRUE);
        dataMap.put(Pair.of("D", "E"), Boolean.TRUE);
        dataMap.put(Pair.of("A", "M"), Boolean.FALSE);
        dataMap.put(Pair.of("M", "N"), Boolean.FALSE);
        dataMap.put(Pair.of("N", "A"), Boolean.FALSE);
        return dataMap;
    }


    @Data
    @Accessors(chain = true)
    public static class Strategy {
        private List<String> configNameList = Lists.newArrayList();
        private Integer min;
        private Integer max;
    }

    private static void priceSumTest() {
        List<Integer> ids = Lists.newArrayList(74, 73, 75, 76);
        NormalUseUtil.out(CollectionUtil.join(ids, SymbolConstantUtil.ENGLISH_COMMA));
        NormalUseUtil.out("" + calculateSum(ids, loadPrice()));
    }

    private static Integer calculateSum(List<Integer> ids, Map<Integer, Integer> priceMap) {
        List<Integer> prices = CollectionUtil.transToList(ids, priceMap::get);
        return CollectionUtil.sum(prices, Integer::intValue).intValue();
    }

    private static Map<Integer, Integer> loadPrice() {
        Map<Integer, Integer> priceMap = Maps.newHashMap();
        priceMap.put(73, 8000);
        priceMap.put(74, 4000);
        priceMap.put(75, 10000);
        priceMap.put(76, 20000);
        priceMap.put(77, 12000);
        priceMap.put(78, 5800);
        priceMap.put(79, 6400);
        priceMap.put(80, 7865);
        priceMap.put(81, 16969);
        return priceMap;
    }

    private static void tarjanAlgorithm() {
        Tarjan.DirectedGraph<String> graph = new Tarjan.DirectedGraph<>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("C", "E");
        graph.addEdge("D", "B");

        graph.buildGraph();
        @SuppressWarnings("unchecked")
        Tarjan<String> t = new Tarjan(graph);
        List<ArrayList<String>> result = t.calculateByIndex();
        // 打印结果
        for (ArrayList<String> elementList : result) {
            for (String s : elementList) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    public static class Tarjan<T> {
        /**
         * 有向图
         */
        private DirectedGraph<T> graph;
        /**
         * 有向图强连通分量索引结果
         */
        private List<ArrayList<Integer>> resultIndexList = Lists.newArrayList();
        /**
         * 有向图顶点是否在计算栈中, 方便查找
         */
        private boolean[] inStack;
        /**
         * 栈
         */
        private Stack<Integer> stack;
        private int[] dfn;
        private int[] low;
        private int time;

        public Tarjan(DirectedGraph<T> graph) {
            this.graph = graph;
            this.inStack = new boolean[graph.getVertexNum()];
            this.stack = new Stack<>();
            dfn = new int[graph.getVertexNum()];
            low = new int[graph.getVertexNum()];
            // 将dfn所有元素都置为-1，其中dfn[i]=-1代表i还有没被访问过。
            Arrays.fill(dfn, -1);
            Arrays.fill(low, -1);
        }

        public List<ArrayList<T>> calculateByIndex() {
            for (int i = 0; i < this.graph.getVertexNum(); i++) {
                if (dfn[i] == -1) {
                    tarjan(i);
                }
            }
            List<ArrayList<T>> resultElementList = Lists.newArrayList();
            for (int i = 0; i < resultIndexList.size(); i++) {
                ArrayList<Integer> itemList = resultIndexList.get(i);
                resultElementList.add(new ArrayList<>(itemList.size()));
                resultElementList.get(i).addAll(CollectionUtil.transToList(itemList, index -> graph.getVertexIndexAsKeyMap().get(index)));
            }
            return resultElementList;
        }

        private void tarjan(int current) {
            dfn[current] = low[current] = time++;
            inStack[current] = true;
            stack.push(current);
            ArrayList<Integer> currentList = graph.getVertexIndexList().get(current);
            for (int next : currentList) {
                // -1代表没有被访问
                if (dfn[next] == -1) {
                    tarjan(next);
                    low[current] = Math.min(low[current], low[next]);
                } else if (inStack[next]) {
                    low[current] = Math.min(low[current], dfn[next]);
                }
            }

            if (low[current] == dfn[current]) {
                ArrayList<Integer> temp = new ArrayList<>();
                int j = -1;
                while (current != j) {
                    j = stack.pop();
                    inStack[j] = false;
                    temp.add(j);
                }
                resultIndexList.add(temp);
            }
        }

        /**
         * 有向图
         *
         * @param <T>
         */
        @Data
        @NoArgsConstructor
        @Accessors(chain = true)
        public static class DirectedGraph<T> {
            /**
             * 有向图顶点数目
             */
            private int vertexNum;
            /**
             * 有向图顶点关系索引列表
             */
            private List<ArrayList<Integer>> vertexIndexList = Lists.newArrayList();
            /**
             * 有向图原始顶点元素与索引的映射关系, 方便查找
             */
            private Map<T, Integer> vertexElementAsKeyMap;
            /**
             * 有向图索引与原始顶点元素的映射关系, 方便查找
             */
            private Map<Integer, T> vertexIndexAsKeyMap;
            /**
             * 有向图原始元素关系列表
             */
            private List<Pair<T, T>> originRelationshipList = Lists.newArrayList();

            /**
             * 添加有向图的边
             *
             * @param u
             * @param v
             */
            public void addEdge(T u, T v) {
                this.originRelationshipList.add(Pair.of(u, v));
            }

            /**
             * 批量添加有向图边
             *
             * @param edgeList
             */
            public void addEdgeList(List<Pair<T, T>> edgeList) {
                this.originRelationshipList.addAll(edgeList);
            }

            /**
             * 构建有向图
             */
            public void buildGraph() {
                Set<T> vertexElements = CollectionUtil.transToSetWithFlatMap(this.getOriginRelationshipList(), pair -> {
                    List<T> elementList = new ArrayList<>();
                    elementList.add(pair.getLeft());
                    elementList.add(pair.getRight());
                    return elementList;
                });
                this.vertexNum = vertexElements.size();
                List<T> vertexElementList = Lists.newArrayList(vertexElements);
                // 排序映射
                vertexElementList.sort(Comparator.comparing(Object::hashCode));
                this.vertexElementAsKeyMap = CollectionUtil.transToMap(vertexElementList, Function.identity(), vertexElementList::indexOf);
                this.vertexIndexAsKeyMap = CollectionUtil.transToMap(this.vertexElementAsKeyMap.entrySet(), Map.Entry::getValue, Map.Entry::getKey);
                this.buildVertexIndex();
            }

            /**
             * 构建有向图的索引
             */
            private void buildVertexIndex() {
                if (CollectionUtil.isEmpty(this.getOriginRelationshipList()) || CollectionUtil.isEmpty(this.getVertexElementAsKeyMap())) {
                    return;
                }
                // 初始化有向图
                for (int i = 0; i < this.vertexNum; i++) {
                    this.vertexIndexList.add(new ArrayList<>());
                }
                for (Pair<T, T> pair : this.originRelationshipList) {
                    Integer outReferVertexIndex = this.vertexElementAsKeyMap.get(pair.getLeft());
                    Integer inVertexIndex = this.vertexElementAsKeyMap.get(pair.getRight());
                    this.vertexIndexList.get(outReferVertexIndex).add(inVertexIndex);
                }
            }
        }

    }

}
