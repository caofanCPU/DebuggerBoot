package com.xyz.caofancpu.util.commonOperateUtils.enumType.mybatis;

import com.xyz.caofancpu.util.commonOperateUtils.enumType.EnumUtil;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.IEnum;
import com.xyz.caofancpu.util.result.GlobalErrorInfoRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * MyBatis枚举类型转换处理器
 *
 * @author caofanCPU
 */
@Slf4j
public class BaseMybatisEnumTypeHandler<E extends Enum<E> & IEnum> extends BaseTypeHandler<IEnum> {

    private final Class<E> type;
    private final E[] enums;

    public BaseMybatisEnumTypeHandler(Class<E> type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (Objects.isNull(this.enums)) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : valueOf(value);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        int value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : valueOf(value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        int value = cs.getInt(columnIndex);
        return cs.wasNull() ? null : valueOf(value);
    }

    private E valueOf(int value) {
        try {
            return EnumUtil.getEnum(enums, IEnum::getValue, value);
        } catch (Exception ex) {
            log.error("枚举转换异常: 值[{}]无法转换为枚举类[{}]", value, type.getSimpleName());
            throw new GlobalErrorInfoRuntimeException("Cannot convert " + value + " to " + type.getSimpleName() + " by ordinal value.");
        }
    }
}
