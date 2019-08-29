package com.xyz.caofancpu.util.commonOperateUtils.enumType;

import com.xyz.caofancpu.util.commonOperateUtils.enumType.mybatis.BaseMybatisEnumTypeHandler;
import com.xyz.caofancpu.util.result.GlobalErrorInfoRuntimeException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 枚举类型自动转换器
 */
@SuppressWarnings("unchecked")
public class AutoDispatchMyBatisEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private BaseTypeHandler typeHandler;

    public AutoDispatchMyBatisEnumTypeHandler(Class<E> enumType) {
        if (Objects.isNull(enumType)) {
            throw new GlobalErrorInfoRuntimeException("参数非法, 类型不能为空");
        }
        if (enumType.isAssignableFrom(IEnum.class)) {
            typeHandler = new BaseMybatisEnumTypeHandler(enumType);
        } else {
            typeHandler = new EnumOrdinalTypeHandler(enumType);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
            throws SQLException {
        typeHandler.setNonNullParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return (E) typeHandler.getNullableResult(rs, columnName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return (E) typeHandler.getNullableResult(rs, columnIndex);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return (E) typeHandler.getNullableResult(cs, columnIndex);
    }
}
