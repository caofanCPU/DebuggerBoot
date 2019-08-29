package com.xyz.caofancpu.trackingtime.interceptor;

import com.xyz.caofancpu.trackingtime.constant.enums.SuccessStatusEnum;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.IEnum;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.mybatis.BaseMybatisEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 业务枚举类型与数据库的转换处理器
 */
@MappedTypes({SuccessStatusEnum.class})
public class EnumToDBHandler<E extends Enum<E> & IEnum> extends BaseMybatisEnumTypeHandler<E> {

    public EnumToDBHandler(Class<E> type) {
        super(type);
    }
}
