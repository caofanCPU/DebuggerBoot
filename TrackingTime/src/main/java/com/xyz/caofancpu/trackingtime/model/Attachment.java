package com.xyz.caofancpu.trackingtime.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by caofanCPU on 2018/6/29.
 *
 * @author caofanCPU
 * 附件
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Attachment implements Serializable {
    @ApiModelProperty(value = "名称", position = 0)
    private String name;

    @ApiModelProperty(value = "业务ID", position = 1)
    private String busId;

    @ApiModelProperty(value = "业务类型编码", position = 2)
    private String type;

    @ApiModelProperty(value = "排序值", position = 3)
    private Integer sortId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", position = 4)
    private String createTime;

    @ApiModelProperty(value = "访问路径", position = 5)
    private String accessUrl;
}