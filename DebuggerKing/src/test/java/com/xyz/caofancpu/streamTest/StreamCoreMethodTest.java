package com.xyz.caofancpu.streamTest;

import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.util.commonOperateUtils.NormalUseUtil;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FileName: StreamCoreMethodTest
 */

public class StreamCoreMethodTest {

    public static void main(String[] args) {
        List<ProductItem> productItemList = ProductDataSource.loadProductItemList();
        if (CollectionUtils.isNotEmpty(productItemList)) {
            // 1. 查找极值
//            testStreamOfFindExtreme(productItemList);
            // 2. 去重
//            testStreamOfNonRepeatable(productItemList);
            // 3. 排序
//            testStreamOfSort(productItemList);
            // 4. 分组/归类/平铺
//            testStreamOfClassify(productItemList);
            // 5. 归约化简(计数求和)
//            testStreamOfSummationExample1(productItemList);
//            testStreamOfSummationExample2(productItemList);

            testNonRepeatableWord();
        }
    }

    /**
     * 使用Stream进行排序 例子: 根据价格 ProductItem.price 进行排序
     *
     * @param productItemList
     */
    public static void testStreamOfSort(List<ProductItem> productItemList) {
//        List<Integer> originList = Ints.asList(IntStream.range(1, 11).toArray());
        viewData("原始列表元素", productItemList);

        productItemList.sort(Comparator.comparing(ProductItem::getPrice));
        viewData("递增方式一(不推荐)排序后列表元素", productItemList);

        productItemList.sort(Comparator.comparing(ProductItem::getPrice, Comparator.reverseOrder()));

        viewData("递减排序方式一(不推荐)后列表元素", productItemList);

        productItemList.sort(Comparator.comparing(ProductItem::getPrice, BigDecimal::compareTo));
        viewData("递增方式二(不推荐)排序后列表元素", productItemList);

        productItemList.sort(Comparator.comparing(ProductItem::getPrice).reversed());
        viewData("递减排序方式二(推荐)后列表元素", productItemList);

        productItemList.sort(Comparator.comparing(ProductItem::getPrice));
        viewData("递增方式三(不推荐)排序后列表元素", productItemList);

        viewData("原始列表元素", productItemList);
        // 使用stream进行排序
        List<ProductItem> ascList = productItemList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ProductItem::getPrice))
                .collect(Collectors.toList());
        viewData("递增方式四(不推荐)排序后列表元素", ascList);

        List<ProductItem> descList = productItemList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ProductItem::getPrice).reversed())
                .collect(Collectors.toList());
        viewData("递减方式四(不推荐)排序后列表元素", descList);

        viewData("原始列表元素", productItemList);
        // 使用Collections.sort()排序(不推荐)
        productItemList.sort(Comparator.comparing(ProductItem::getPrice));
        viewData("递增方式五(不推荐)排序后列表元素", productItemList);
        productItemList.sort(Comparator.comparing(ProductItem::getPrice).reversed());
        viewData("递减方式五(不推荐)排序后列表元素", productItemList);
    }

    /**
     * 使用Stream进行分类, 例子: 根据颜色 ProductItem.color 进行分组归类
     *
     * @param productItemList
     */
    public static void testStreamOfClassify(List<ProductItem> productItemList) {
        viewData("原始列表数据", productItemList);
        Map<String, List<ProductItem>> classifiedMap = productItemList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(ProductItem::getColor, Collectors.toList()));
        viewData("分组归类为Map后的数据", classifiedMap);
        List<List<ProductItem>> classifiedList = classifiedMap.keySet().stream()
                .filter(Objects::nonNull)
                .map(classifiedMap::get)
                .collect(Collectors.toList());
        viewData("分组归类为List后的数据", classifiedList);
        List<List<ProductItem>> fastClassifiedList = new ArrayList<>();
        productItemList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(ProductItem::getColor, Collectors.toList()))
                .forEach((key, value) -> fastClassifiedList.add(value));
        viewData("快速分组归类为List后的数据", fastClassifiedList);

        List<ProductItem> flattedList = fastClassifiedList.stream()
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        viewData("齐嵌套列表扁平化展开后的数据", flattedList);
    }

    /**
     * 使用Stream寻找极值, 例子: 寻找列表中累计销量 ProductItem.salesNum最大值和最小值
     *
     * @param productItemList
     */
    public static void testStreamOfFindExtreme(List<ProductItem> productItemList) {
        viewData("原始列表数据", productItemList);
        // 获取累计销量Set
        Set<Integer> salesNumSet = productItemList.stream()
                .filter(Objects::nonNull)
                .mapToInt(ProductItem::getSalesNum)
                .boxed()
                .collect(Collectors.toSet());
        viewData("原始累计销量列表(去重后)数据", salesNumSet);
        int maxSalesNum = productItemList.stream()
                .filter(Objects::nonNull)
                .max((x, y) -> x.getSalesNum() - y.getSalesNum() > 0 ? 1 : -1)
                .get()
                .getSalesNum();
        viewData("最大累计销量", maxSalesNum);
        int minSalesNum = productItemList.stream()
                .filter(Objects::nonNull)
                .min((x, y) -> x.getSalesNum() - y.getSalesNum() > 0 ? 1 : -1)
                .get()
                .getSalesNum();
        viewData("最小累计销量", minSalesNum);
    }

    /**
     * 使用Stream去除重复元素(元素对象需要复写equals方法和hashCode方法), 例子: 根据颜色 ProductItem.color 去重
     *
     * @param productItemList
     */
    public static void testStreamOfNonRepeatable(List<ProductItem> productItemList) {
        viewData("原始列表数据", productItemList);
        viewData("原始列表大小", productItemList.size());
        List<ProductItem> nonRepeatableList = productItemList.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        viewData("根据颜色去重后的列表数据", nonRepeatableList);
        viewData("根据颜色去重后的列表大小", nonRepeatableList.size());
    }

    /**
     * 使用Stream归约求和, 例子: 计算列表的累计销量 SUM(ProductItem.salesNum)
     *
     * @param productItemList
     */
    public static void testStreamOfSummationExample1(List<ProductItem> productItemList) {
        viewData("原始列表数据", productItemList);
        int normalSum = 0;
        for (ProductItem item : productItemList) {
            if (Objects.isNull(item)) {
                continue;
            }
            normalSum += item.getSalesNum();
        }
        viewData("使用普通for循环计算累计销量: ", normalSum);

        Integer enhanceSum1 = productItemList.stream()
                .filter(Objects::nonNull)
                .map(ProductItem::getSalesNum)
                .reduce(0, Integer::sum);
        viewData("使用增强归约方式一(推荐)求和计算累计销量: ", enhanceSum1);

        Integer enhanceSum2 = productItemList.stream()
                .filter(Objects::nonNull)
                // mapToInt(ProductItem::getSalesNum) <==> mapToInt(param -> param.getSalesNum())
                .mapToInt(ProductItem::getSalesNum)
                .sum();
        viewData("使用增强归约方式二(推荐)求和计算累计销量:", enhanceSum2);

        Integer enhanceSum3 = productItemList.stream()
                .filter(Objects::nonNull)
                .map(ProductItem::getSalesNum)
                .reduce(Integer::sum)
                .get();
        viewData("使用增强归约方式三(推荐)求和计算累计销量:", enhanceSum3);
    }

    /**
     * 使用Stream归约求和, 例子: 计算列表的累计销售额 SUM(ProductItem.price * ProductItem.salesNum)
     *
     * @param productItemList
     */
    public static void testStreamOfSummationExample2(List<ProductItem> productItemList) {
        viewData("原始列表数据", productItemList);
        BigDecimal normalSum = BigDecimal.valueOf(0);
        for (ProductItem item : productItemList) {
            if (Objects.isNull(item)) {
                continue;
            }
            normalSum = normalSum.add(item.getPrice().multiply(BigDecimal.valueOf(item.getSalesNum())));
        }
        viewData("使用普通for循环计算总销售额: ", normalSum);

        BigDecimal enhanceSum1 = productItemList.stream()
                .filter(Objects::nonNull)
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getSalesNum())))
                // reduce(BigDecimal.valueOf(0), (x, y) -> x.add(y)) <==> reduce(BigDecimal.valueOf(0), BigDecimal::add)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
        viewData("使用增强归约方式一(推荐)求和计算总销售额: ", enhanceSum1);

        double enhanceSum = productItemList.stream()
                .filter(Objects::nonNull)
                .mapToDouble(item -> Double.valueOf(item.getPrice().multiply(BigDecimal.valueOf(item.getSalesNum())).toString()))
                .sum();
        viewData("使用增强归约方式二(推荐)求和计算总销售额: ", enhanceSum);

        BigDecimal enhanceSum3 = productItemList.stream()
                .filter(Objects::nonNull)
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getSalesNum())))
                // reduce((x, y) -> x.add(y)) <==> reduce(BigDecimal::add)
                .reduce(BigDecimal::add)
                .get();
        viewData("使用增强归约方式三(推荐)求和计算总销售额: ", enhanceSum3);
    }

    private static void testNonRepeatableWord() {
        List<String> originList = Arrays.asList("Hello", "World");
        // 传统使用for循环方法
        StringBuilder sb = new StringBuilder();
        for (String element : originList) {
            sb.append(element);
        }
        char[] wordArray = sb.toString().toCharArray();
        Set<Character> wordSet = new HashSet<>(16, 0.75f);
        for (char c : wordArray) {
            wordSet.add(c);
        }
        viewData("去重后的字母列表", wordSet);

        // 流式操作
        Set<Character> nonRepeatableWordSet = originList.stream()
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                // 去重可去掉, 因为后续Collectors.toSet()本身可保证去重
                .distinct()
                .map(s -> s.charAt(0))
                .collect(Collectors.toSet());
        viewData("去重后的字母列表", nonRepeatableWordSet);
    }

    public static void testLamdaExpression() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("启动一个线程");
            }
        }).start();


        new Thread(() -> System.out.println("启动一个线程")).start();

    }


    public static void viewData(String msg, Object data) {
        NormalUseUtil.out(msg + "\n" + JSONUtil.formatStandardJSON(JSONObject.toJSONString(data)));
    }

}
