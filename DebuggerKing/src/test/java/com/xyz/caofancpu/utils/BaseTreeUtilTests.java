package com.xyz.caofancpu.utils;

import com.xyz.caofancpu.util.commonOperateUtils.treeElement.BaseTree;
import com.xyz.caofancpu.util.commonOperateUtils.treeElement.BaseTreeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


/**
 *
 */
public class BaseTreeUtilTests {

    public static void main(String[] args) {
//        testBaseTree();
        testChildTree();
    }

    public static void testBaseTree() {
        List<BaseTree> treeList = buildBaseTree();
        List<BaseTree> expandList = new ArrayList<>();
        List<BaseTree> leafLimitedList = new ArrayList<>();
        BaseTreeUtil.expandTreeElements(expandList, treeList);
        BaseTreeUtil.findTreeLeafElements(leafLimitedList, treeList, 3);
    }

    public static void testChildTree() {
        List<ChildTree> treeList = buildChildTree();
        List<ChildTree> expandList = new ArrayList<>();
        List<ChildTree> leafLimitedList = new ArrayList<>();
        BaseTreeUtil.expandTreeElements(expandList, treeList);
        BaseTreeUtil.findTreeLeafElements(leafLimitedList, treeList, 3);
    }

    public static List<BaseTree> buildBaseTree() {
        List<BaseTree> treeList = LongStream.range(1, 7).boxed()
                .map(id -> {
                    long depth = id % 3 == 0 ? 3 : id % 3;
                    return new BaseTree().setId(id).setDepth((int) depth);
                })
                .collect(Collectors.toList());
        treeList.get(0).getChildren().add(treeList.get(1));
        treeList.get(1).getChildren().add(treeList.get(2));
        treeList.get(3).getChildren().add(treeList.get(4));

        treeList.get(5).setDepth(1);
        return new ArrayList<BaseTree>() {
            {
                add(treeList.get(0));
                add(treeList.get(3));
                add(treeList.get(5));
            }
        };
    }

    public static List<ChildTree> buildChildTree() {
        List<ChildTree> treeList = LongStream.range(1, 7).boxed()
                .map(id -> {
                    long depth = id % 3 == 0 ? 3 : id % 3;
                    ChildTree childTree = new ChildTree();
                    childTree.setId(id);
                    childTree.setDepth((int) depth);
                    return childTree;
                })
                .collect(Collectors.toList());
        treeList.get(0).getChildren().add(treeList.get(1));
        treeList.get(1).getChildren().add(treeList.get(2));
        treeList.get(3).getChildren().add(treeList.get(4));

        treeList.get(5).setDepth(1);
        return new ArrayList<ChildTree>() {
            {
                add(treeList.get(0));
                add(treeList.get(3));
                add(treeList.get(5));
            }
        };
    }

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    @AllArgsConstructor
    public static class ChildTree extends BaseTree {

    }
}
