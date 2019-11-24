package com.xyz.caofancpu.trackingtime.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 时间印记详表
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TimeMarkDetail {
    @ApiModelProperty(value = "主键ID", example = "8")
    private Long id;

    @ApiModelProperty(value = "主表关联ID", example = "101")
    private Long markContentId;

    @ApiModelProperty(value = "内容", example = "周星特异功能到澳门, 豪赌成功竟成神")
    private String content;

    @ApiModelProperty(value = "内容签名", example = "SRC-ginna78XDJEKj--ln")
    private String contentSignature;

    @ApiModelProperty(value = "加密类型:0;1;2", example = "1:OK")
    private Integer encryptType;

    @ApiModelProperty(value = "创建时间", example = "2019-08-12 13:14:15")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2019-08-12 13:14:15")
    private LocalDateTime updateTime;

}
