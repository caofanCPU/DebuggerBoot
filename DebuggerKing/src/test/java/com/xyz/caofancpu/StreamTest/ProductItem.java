package com.xyz.caofancpu.StreamTest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * FileName: ProductItem
 *
 * @author: caofanCPU
 * @date: 2019/3/5 16:36
 */
@Data
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Accessors(chain = true)
@ApiModel
public class ProductItem {
    @ApiModelProperty(value = "主键Id", name = "id", required = false, example = "2019")
    private int id;
    
    @ApiModelProperty(value = "商品价格", name = "price", required = false, example = "32.50")
    private BigDecimal price;
    
    @ApiModelProperty(value = "商品颜色", name = "color", required = false, example = "Red")
    private String color;
    
    @ApiModelProperty(value = "商品累计销量", name = "salesNum", required = false, example = "177")
    private int salesNum;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductItem that = (ProductItem) o;
        return Objects.equals(color, that.color);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
