package com.xyz.caofancpu.trackingtime.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 时间印记签名主表
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TimeMarkContent {
    @ApiModelProperty(value = "主键Id", name = "id", example = "1")
    private Long id;

    @ApiModelProperty(value = "时间区块ID", name = "timeBlockId", example = "98789")
    private Long timeBlockId;

    @ApiModelProperty(value = "标题", name = "title", example = "今晚打老虎")
    private String title;

    @ApiModelProperty(value = "创作者ID", name = "authorId", example = "10086")
    private Long authorId;

    @ApiModelProperty(value = "创作者笔名", name = "authorName", example = "帝八哥")
    private String authorName;

    @ApiModelProperty(value = "摘要关键字:|,|", name = "keyword", example = "域名,网站,起飞")
    private String keyword;

    @ApiModelProperty(value = "签名二维码", name = "signCodeUrl", example = "1xceljo993dcbxXldelelM==")
    private String signCodeUrl;

    @ApiModelProperty(value = "状态：0;1;-1;9", name = "status", example = "9")
    private Integer status;

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-08-12 13:14:15")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "2019-08-12 13:14:15")
    private LocalDateTime updateTime;

}
