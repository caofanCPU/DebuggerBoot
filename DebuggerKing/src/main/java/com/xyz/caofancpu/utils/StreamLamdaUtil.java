package com.xyz.caofancpu.utils;

import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.model.Area;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author CY_XYZ
 * @date 2018/7/29
 */
public class StreamLamdaUtil {
    
    /**
     * 起始时间戳
     */
    private static long startTime = 0L;
    
    /**
     * 结束时间戳
     */
    private static long endTime = 0L;
    
    /**
     * 重启标识
     */
    private static boolean restart = true;
    
    public static void main(String[] args) {
        positionTime();
        testAreaByStreamLamda();
        positionTime();
        outputExcuteTime();
    }
    
    /**
     * 定位时间戳，毫秒
     */
    public static void positionTime() {
        if (restart) {
            startTime = System.currentTimeMillis();
        } else {
            endTime = System.currentTimeMillis();
        }
        restart = !restart;
    }
    
    /**
     * 输出执行时间
     */
    public static void outputExcuteTime() {
        System.out.println("\n执行时间：" + (endTime - startTime) + "ms");
    }
    
    /**
     * 测试集合的流：stream，lamda表达式
     * 自动分类函数：groupingBy
     */
    public static void testAreaByStreamLamda() {
        // 初始化省市区数据
        List<Area> provinceList = initProvinceList();
        List<Area> cityList = initCityList();
        List<Area> countyList = initCountyList();
        
        // 市区根据pid分类
        Map<Integer, List<Area>> cityClassifiedMap = classify(cityList);
        Map<Integer, List<Area>> countyClassifiedMap = classify(countyList);
        
        // 织入省市区树状
        handleData(provinceList, cityClassifiedMap);
        handleData(cityList, countyClassifiedMap);
        String jsonString = JSONObject.toJSONString(provinceList);
        output(JSONUtil.formatStandardJSON(jsonString));
    }
    
    public static void output(String jsonString) {
        System.out.println(jsonString);
    }
    
    /**
     * 将映射关系的数据塞入父类
     *
     * @param parentList
     * @param childMap
     */
    public static void handleData(List<Area> parentList, Map<Integer, List<Area>> childMap) {
        parentList.forEach(item -> item.setChildren(childMap.get(item.getId())));
    }
    
    /**
     * 利用流的自动分类功能
     *
     * @param areaList
     * @return
     */
    public static Map<Integer, List<Area>> classify(List<Area> areaList) {
        // 方式一：顺序流处理，处理结果保持入参的顺序
        return areaList.stream().collect(Collectors.groupingBy(Area::getPid));

//        // 方式二：并行流处理，通过多线程，map-reduce；但是注意：并行流使用在大数据量场景下，否则性能反而低
//        return areaList.parallelStream().collect(Collectors.groupingBy(Area::getPid));
    }
    
    /**
     * 初始化省的数据
     * 可用查询数据库替代
     *
     * @return
     */
    public static List<Area> initProvinceList() {
        return Stream.of(
                new Area(12, "湖北省", 0),
                new Area(25, "北京市", 0)
        ).collect(Collectors.toList());
    }
    
    public static List<Area> initCityList() {
        return Stream.of(
                new Area(120, "武汉市", 12),
                new Area(121, "咸宁市", 12),
                new Area(122, "天门市", 12),
                new Area(251, "北京县", 25),
                new Area(254, "保定县", 25)
        ).collect(Collectors.toList());
    }
    
    public static List<Area> initCountyList() {
        return Stream.of(
                new Area(1200, "洪山区", 120),
                new Area(1201, "武昌区", 120),
                new Area(1202, "汉口区", 120),
                new Area(1210, "嘉鱼县", 121),
                new Area(2511, "东城区", 251),
                new Area(2544, "雄安区", 254)
        ).collect(Collectors.toList());
    }
    
    public Map<Integer, Area> convertToMap(List<Area> areaList) {
        Map<Integer, Area> resultMap = areaList.stream().collect(Collectors.toMap(Area::getId, Function.identity()));
        return resultMap;
    }
    
}
