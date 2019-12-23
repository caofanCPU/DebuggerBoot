package com.xyz.caofancpu.util.commonoperateutils.enumtype;

import org.apache.commons.lang3.StringUtils;

/**
 * 枚举接口
 */
public interface IEnum {
    /**
     * 要存入数据库的类型值
     *
     * @return
     */
    Integer getValue();

    /**
     * 业务或代码上的类型名称(中文|英文)
     *
     * @return
     */
    String getName();

    /**
     * 类型别名, 可用于前端展示
     * 默认为空串
     *
     * @return
     */
    default String getViewName() {
        return StringUtils.EMPTY;
    }
}
