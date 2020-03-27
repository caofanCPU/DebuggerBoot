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
import java.util.function.Function;

/**
 * 树型结构处理工具类测试用例
 *
 * @author caofanCPU
 */
public class WrapTreeUtilTests {

    public static void main(String[] args) {
        testInitTree();
    }

    public static void testInitTree() {
        List<Area> areaList = buildAreaNestedList();
        NormalUseUtil.out("原始树\n" + JSONUtil.formatStandardJSON(areaList));
        testInitTreeByPid(areaList);
        NormalUseUtil.out("\n=========================\n");
        testInitTreeByChildren(areaList);
    }

    /**
     * 使用情况: 在一些第三方接口中返回的字段用'_'而非驼峰, 因而产生树[Area]转换为树[Area]的需求
     * 根据pid装换树
     */
    public static void testInitTreeByPid(List<Area> areaList) {
        List<Area> nonNestedList = new ArrayList<>();
        WrapTreeUtil.expandNestedListInOrder(nonNestedList, areaList, Area::getChildren, Function.identity());
        List<WrapTree<Integer, Area>> resultTreeList = WrapTreeUtil.initTreeByPid(nonNestedList, Area::getPid, Area::getId, Area::getDepth, Area::getName, Area::getSortNo, Boolean.TRUE);
        NormalUseUtil.out("根据pid转换平铺型List树结果:\n" + JSONUtil.formatStandardJSON(resultTreeList));
    }

    /**
     * 使用情况: 在一些第三方接口中返回的字段用'_'而非驼峰, 因而产生树[Area]转换为树[Area]的需求
     * 根据children转换树
     */
    public static void testInitTreeByChildren(List<Area> areaList) {
        List<WrapTree<Integer, Area>> resultTreeList = WrapTreeUtil.initTreeByChildren(areaList, Area::getChildren, Area::getId, Area::getDepth, Area::getName, Area::getSortNo, Boolean.TRUE);
        NormalUseUtil.out("根据children转换嵌套型List树结果\n" + JSONUtil.formatStandardJSON(resultTreeList));
    }

    public static void testExpandNestedListInOrder() {
        List<Area> areaList = buildAreaNestedList();
        List<Area> resultList = new ArrayList<>();
        WrapTreeUtil.expandNestedListInOrder(resultList, areaList, Area::getChildren, Function.identity());
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
        Area beijingProvince = new Area(2, "北京市", 0, 1, 3);

        Area sichuanProvince = new Area(3, "四川省", 0, 1, 1);
        Area chengduCity = new Area(30, "成都市", 3, 2, 1);

        Area hubeiProvince = new Area(1, "湖北省", 0, 1, 2);
        Area wuhanCity = new Area(10, "武汉市", 1, 2, 2);
        Area xiangyangCity = new Area(11, "襄阳市", 1, 2, 1);
        Area hongshanCounty = new Area(100, "洪山区", 10, 3, 1);

        List<Area> areaList = CollectorGenerateUtil.initArrayList(list -> {
            list.add(beijingProvince);
            list.add(sichuanProvince);
            list.add(chengduCity);
            list.add(hubeiProvince);
            list.add(wuhanCity);
            list.add(xiangyangCity);
            list.add(hongshanCounty);
            return list;
        });
        areaList.sort(Comparator.comparing(Area::getPid).reversed());
        return areaList;
    }

    public static List<Area> buildAreaNestedList() {
        Area beijingProvince = new Area(2, "北京市", 0, 1, 3);

        Area sichuanProvince = new Area(3, "四川省", 0, 1, 1);
        Area chengduCity = new Area(30, "成都市", 3, 2, 1);
        sichuanProvince.setChildren(CollectorGenerateUtil.initArrayList(children -> {
            children.add(chengduCity);
            children.sort(Comparator.comparing(Area::getPid).reversed());
            return children;
        }));

        Area hubeiProvince = new Area(1, "湖北省", 0, 1, 2);
        Area wuhanCity = new Area(10, "武汉市", 1, 2, 2);
        Area xiangyangCity = new Area(11, "襄阳市", 1, 2, 1);
        Area hongshanCounty = new Area(100, "洪山区", 10, 3, 1);
        wuhanCity.setChildren(CollectorGenerateUtil.initArrayList(children -> {
            children.add(hongshanCounty);
            children.sort(Comparator.comparing(Area::getPid).reversed());
            return children;
        }));
        hubeiProvince.setChildren(CollectorGenerateUtil.initArrayList(children -> {
            children.add(wuhanCity);
            children.add(xiangyangCity);
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
         * 节点相对排序值
         */
        private Integer sortNo;

        /**
         * 子节点集合
         */
        private List<Area> children;

        public Area(Integer id, String name, Integer pid) {
            this.id = id;
            this.name = name;
            this.pid = pid;
        }

        public Area(Integer id, String name, Integer pid, Integer depth, Integer sortNo) {
            this.id = id;
            this.name = name;
            this.pid = pid;
            this.depth = depth;
            this.sortNo = sortNo;
        }
    }
}
