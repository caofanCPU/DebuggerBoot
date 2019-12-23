package com.xyz.caofancpu.trackingtime.zcy;

import com.xyz.caofancpu.util.commonoperateutils.NormalUseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caofanCPU
 */
public class FollowMeAtFirst {

    public static void main(String[] args) {
        List<String> list = whatHasDoneInTheMorning();
        for (String note : list) {
            // 用工具类方法将结果打印在控制台
            NormalUseUtil.out(note);
        }
        NormalUseUtil.out("\n\n");
        Map<Integer, String> dictionary = whatHasDoneWithOrder();
        dictionary.forEach((key, value) -> NormalUseUtil.out(key + ". " + value));
    }

    /**
     * 早上做的事情清单
     *
     * @return
     */
    private static List<String> whatHasDoneInTheMorning() {
        // 准备一个收集容器, 用"清单"来记录事情
        List<String> list = new ArrayList<>();
        // 把做的事情"添加"到清单中
        list.add("Get Up");
        list.add("Wash Teeth");
        list.add("Pick up package");
        list.add("Buy breakfast");
        return list;
    }

    private static Map<Integer, String> whatHasDoneWithOrder() {
        List<String> list = whatHasDoneInTheMorning();
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            dictionary.put(i, list.get(i));
        }
        return dictionary;
    }

}
