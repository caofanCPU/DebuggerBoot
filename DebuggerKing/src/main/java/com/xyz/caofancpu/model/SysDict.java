package com.xyz.caofancpu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
