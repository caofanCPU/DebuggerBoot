package com.xyz.caofancpu.util.commonOperateUtils.enumType.mybatis;

import com.xyz.caofancpu.util.commonOperateUtils.enumType.EnumUtil;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.IEnum;
import com.xyz.caofancpu.util.result.GlobalErrorInfoRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

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
@MappedTypes({IEnum.class})
@Slf4j
public class MybatisEnumTypeHandler<E extends Enum<E> & IEnum> extends BaseTypeHandler<IEnum> {

    private Class<E> enumType;

    public MybatisEnumTypeHandler(Class<E> enumType) {
        if (Objects.isNull(enumType)) {
            throw new GlobalErrorInfoRuntimeException("参数非法, 类型不能为空");
        }
        this.enumType = enumType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return rs.wasNull() ? null : valueOf(rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.wasNull() ? null : valueOf(rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.wasNull() ? null : valueOf(cs.getInt(columnIndex));
    }

    private E valueOf(int value) {
        try {
            return EnumUtil.getEnumByValue(enumType, value);
        } catch (Exception e) {
            log.error("枚举转换异常: 值[{}]无法转换为枚举类[{}]", value, enumType.getSimpleName());
            throw new GlobalErrorInfoRuntimeException("枚举转换异常");
        }
    }
}
