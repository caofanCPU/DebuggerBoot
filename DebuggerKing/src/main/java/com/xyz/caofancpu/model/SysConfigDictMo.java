package com.xyz.caofancpu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel
@Data
@Accessors(chain = true)
public class SysConfigDictMo {

    @ApiModelProperty(value = "主键Id", name = "id", required = false, example = "10000")
    private Integer id;

    @ApiModelProperty(value = "父ID", name = "pid", required = false, example = "20")
    private Integer pid;

    @ApiModelProperty(value = "编码CODE", name = "code", required = false, example = "wx.domain")
    private String code;

    @ApiModelProperty(value = "名称", name = "name", required = false, example = "zcy")
    private String name;

    @ApiModelProperty(value = "排序", name = "sortId", required = false, example = "1")
    private Integer sortId;

}
