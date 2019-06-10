package com.xyz.caofancpu.util.dataOperateUtils.FunctionInterface;

import java.util.List;

/**
 * FileName: HashMapGeneratorInterface
 *
 * @author: caofanCPU
 * @date: 2019/3/4 12:12
 */
@FunctionalInterface
public interface ArrayListGeneratorInterface<E> {
    
    /**
     * 该函数名称, 入参, 返回值不影响任意
     *
     * @param arrayList
     * @return
     */
    List<E> process(List<E> arrayList);
    
}
