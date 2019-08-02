package com.xyz.caofancpu.util.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 键值数据对象
 * 根据 Key && Value 决定唯一性
 */
@Getter
@Accessors(chain = true)
@AllArgsConstructor
public class KeVin<K, V> {
    private K key;
    private V value;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KeVin)) {
            return Boolean.FALSE;
        }
        KeVin keVin = (KeVin) o;
        return new EqualsBuilder().append(this.key, keVin.key).append(this.value, keVin.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.key).append(this.value).hashCode();
    }

}
