package com.xyz.caofancpu.trackingtime.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysDict {

    @ApiModelProperty(value = "主键Id", name = "id", required = false, example = "10000")
    private Long id;

    @ApiModelProperty(value = "编码CODE", name = "code", required = false, example = "wx.domain")
    private String code;

    @ApiModelProperty(value = "名称", name = "name", required = false, example = "zcy")
    private String name;

    @ApiModelProperty(value = "分组ID", name = "groupId", required = false, example = "20")
    private Long groupId;

    @ApiModelProperty(value = "排序", name = "sortId", required = false, example = "1")
    private Long sortId;

    @ApiModelProperty(value = "价格", name = "money", required = false, example = "52013.14")
    private BigDecimal money;
}
