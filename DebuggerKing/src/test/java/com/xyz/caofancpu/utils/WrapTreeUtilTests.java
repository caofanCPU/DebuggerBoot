package com.xyz.caofancpu.utils;

import com.xyz.caofancpu.model.Area;
import com.xyz.caofancpu.util.commonOperateUtils.treeElement.WrapTreeUtil;
import com.xyz.caofancpu.util.dataOperateUtils.CollectorGenerateUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class WrapTreeUtilTests {
    
    public static void main(String[] args) {
        testExpandTreeElements();
        testCollectTreeLeafElements();
    }
    
    public static void testExpandTreeElements() {
        List<Area> areaList = buildAreaList();
        List<Area> resultList = new ArrayList<>();
        WrapTreeUtil.expandTreeElements(resultList, areaList, Area::getPid, Area::getId, null);
        int a = 1;
    }
    
    public static void testCollectTreeLeafElements() {
        List<Area> areaList = buildAreaList();
        List<Area> resultList = new ArrayList<>();
        WrapTreeUtil.collectTreeLeafElements(resultList, areaList, Area::getPid, Area::getId, Area::getDepth, 1);
        int a = 1;
    }
    
    public static List<Area> buildAreaList() {
        Area beijingProvince = new Area(2, "北京市", 0, 1);
        
        Area sichuanProvince = new Area(3, "四川省", 0, 1);
        Area chengduCity = new Area(30, "成都市", 3, 2);
        
        Area hubeiProvince = new Area(1, "湖北省", 0, 1);
        Area wuhanCity = new Area(10, "武汉市", 1, 2);
        Area hongshanCounty = new Area(100, "洪山区", 10, 3);
        
        List<Area> areaList = CollectorGenerateUtil.initArrayList(list -> {
            list.add(beijingProvince);
            list.add(sichuanProvince);
            list.add(chengduCity);
            list.add(hubeiProvince);
            list.add(wuhanCity);
            list.add(hongshanCounty);
            return list;
        });
        areaList.sort(Comparator.comparing(Area::getPid).reversed());
        return areaList;
    }
    
    
}
