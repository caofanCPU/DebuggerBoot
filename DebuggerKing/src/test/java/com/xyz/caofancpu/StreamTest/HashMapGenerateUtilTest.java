package com.xyz.caofancpu.StreamTest;


import com.xyz.caofancpu.util.dataOperateUtils.CollectorGenerateUtil;

import java.util.Map;
import java.util.Objects;

/**
 * FileName: HashMapGenerateUtilTest
 */

public class HashMapGenerateUtilTest {


    public static void main(String[] args) {
        /*Map<String, Object> hashMap = new HashMap<String, Object>(4, 0.75f) {
            {
                put("元素1", 2);
                put("元素2", "流式操作");
            }
        };*/

        Map<String, Object> hashMap = CollectorGenerateUtil.initHashMap(3, 0.75f, map -> {
            map.put("元素1", 2);
            map.put("元素2", "流式操作");
            return map;
        });

        // 输出Map中的元素, 键值形式
        // 方式一
        hashMap.keySet().parallelStream()
                .filter(Objects::nonNull)
                .forEach(key -> System.out.println("键 = [" + key + "]\t值 = [" + hashMap.get(key) + "]"));

        // 方式二
//        hashMap.forEach((key, value) -> System.out.println("键 = [" + key + "]\t值 = [" + value + "]"));
    }

}
