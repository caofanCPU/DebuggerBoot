package com.xyz.caofancpu.utils;

import com.xyz.caofancpu.util.commonoperateutils.NormalUseUtil;
import com.xyz.caofancpu.util.commonoperateutils.treeelement.WrapTree;
import com.xyz.caofancpu.util.commonoperateutils.treeelement.WrapTreeUtil;
import com.xyz.caofancpu.util.dataoperateutils.CollectorGenerateUtil;
import com.xyz.caofancpu.util.dataoperateutils.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 树型结构处理工具类测试用例
 *
 * @author caofanCPU
 */
public class WrapTreeUtilTests {

    public static void main(String[] args) {
        testInitTreeByPid();
//        NormalUseUtil.out("\n=========================\n");
//        testInitTreeByChildren();
    }

    /**
     * 使用情况: 在一些第三方接口中返回的字段用'_'而非驼峰, 因而产生树[Area]转换为树[Area]的需求
     * 根据pid装换树
     */
    public static void testInitTreeByPid() {
        List<Area> areaList = buildAreaNestedList();
        List<Area> nonNestedList = new ArrayList<>();
        WrapTreeUtil.expandNestedListInOrder(nonNestedList, areaList, Area::getChildren);
        List<WrapTree<Integer, Area>> resultTreeList = WrapTreeUtil.initTreeByPid(nonNestedList, Area::getPid, Area::getId, Area::getDepth, Area::getName, Boolean.TRUE);
        NormalUseUtil.out("原始树\n" + JSONUtil.formatStandardJSON(areaList));
        NormalUseUtil.out("转换树\n" + JSONUtil.formatStandardJSON(resultTreeList));
    }

    /**
     * 使用情况: 在一些第三方接口中返回的字段用'_'而非驼峰, 因而产生树[Area]转换为树[Area]的需求
     * 根据children转换树
     */
    public static void testInitTreeByChildren() {
        List<Area> areaList = buildAreaNestedList();
        List<WrapTree<Integer, Area>> resultTreeList = WrapTreeUtil.initTreeByChildren(areaList, Area::getChildren, Area::getId, Area::getDepth, Area::getName, Boolean.TRUE);
        NormalUseUtil.out("原始树\n" + JSONUtil.formatStandardJSON(areaList));
        NormalUseUtil.out("转换树\n" + JSONUtil.formatStandardJSON(resultTreeList));
    }

    public static void testExpandNestedListInOrder() {
        List<Area> areaList = buildAreaNestedList();
        List<Area> resultList = new ArrayList<>();
        WrapTreeUtil.expandNestedListInOrder(resultList, areaList, Area::getChildren);
        int a = 1;
    }

    public static void testCutTreeElementByDepth() {
        List<Area> areaList = buildAreaNestedList();
        List<Area> resultList = WrapTreeUtil.cutTreeElementByDepth(areaList, 3, Area::getChildren, Area::getDepth);
        int a = 1;
    }

    public static void testSelectTreeLeafElements() {
        List<Area> areaList = buildAreaNestedList();
        List<Area> resultList = new ArrayList<>();
        WrapTreeUtil.selectRelativeTreeLeafByDepth(resultList, areaList, 2, Area::getChildren, Area::getId, Area::getDepth);
        int a = 1;
    }

    public static void testPureSelectTreeLeafElements() {
        List<Area> areaList = buildAreaNestedList();
        List<Area> resultList = new ArrayList<>();
        WrapTreeUtil.pureSelectRelativeTreeLeafByDepth(resultList, areaList, 1, Area::getChildren, Area::getId, Area::getDepth);
        int a = 1;
    }

    public static void testExpandTreeElements() {
        List<Area> areaList = buildAreaList();
        List<Area> resultList = new ArrayList<>();
        WrapTreeUtil.expandTreeElements(resultList, areaList, Area::getPid, Area::getId);
        int a = 1;
    }

    public static void testCollectTreeLeafElements() {
        List<Area> areaList = buildAreaList();
        List<Area> resultList = new ArrayList<>();
        WrapTreeUtil.collectRelativeTreeLeafElements(resultList, areaList, 1, Area::getPid, Area::getId, Area::getDepth);
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

    public static List<Area> buildAreaNestedList() {
        Area beijingProvince = new Area(2, "北京市", 0, 1);

        Area sichuanProvince = new Area(3, "四川省", 0, 1);
        Area chengduCity = new Area(30, "成都市", 3, 2);
        sichuanProvince.setChildren(CollectorGenerateUtil.initArrayList(children -> {
            children.add(chengduCity);
            children.sort(Comparator.comparing(Area::getPid).reversed());
            return children;
        }));

        Area hubeiProvince = new Area(1, "湖北省", 0, 1);
        Area wuhanCity = new Area(10, "武汉市", 1, 2);
        Area hongshanCounty = new Area(100, "洪山区", 10, 3);
        wuhanCity.setChildren(CollectorGenerateUtil.initArrayList(children -> {
            children.add(hongshanCounty);
            children.sort(Comparator.comparing(Area::getPid).reversed());
            return children;
        }));
        hubeiProvince.setChildren(CollectorGenerateUtil.initArrayList(children -> {
            children.add(wuhanCity);
            children.sort(Comparator.comparing(Area::getPid).reversed());
            return children;
        }));

        List<Area> areaList = CollectorGenerateUtil.initArrayList(list -> {
            list.add(beijingProvince);
            list.add(sichuanProvince);
            list.add(hubeiProvince);
            return list;
        });
        areaList.sort(Comparator.comparing(Area::getPid).reversed());
        return areaList;
    }

    /**
     * 区域对象
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Area implements Serializable {

        /**
         * ID
         */
        private Integer id;

        /**
         * 名称
         */
        private String name;

        /**
         * pid
         */
        private Integer pid;

        /**
         * 节点深度
         */
        private Integer depth;

        /**
         * 子节点集合
         */
        private List<Area> children;

        public Area(Integer id, String name, Integer pid) {
            this.id = id;
            this.name = name;
            this.pid = pid;
        }

        public Area(Integer id, String name, Integer pid, Integer depth) {
            this.id = id;
            this.name = name;
            this.pid = pid;
            this.depth = depth;
        }
    }
}
