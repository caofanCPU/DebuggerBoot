package com.xyz.caofancpu.trackingtime.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 内容VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TimeMarkContentVO {
    @ApiModelProperty(value = "时间区块ID", name = "timeBlockId", example = "98789")
    public Long timeBlockId;

    @ApiModelProperty(value = "内容主Id", name = "id", example = "101")
    private Long id;

    @ApiModelProperty(value = "起始时间", name = "startTime", example = "2019-08-12 12:11:10")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间", name = "endTime", example = "2019-08-12 13:14:15")
    private LocalDateTime endTime;

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
    private int status;

    @ApiModelProperty(value = "内容", name = "content", example = "周星特异功能到澳门, 豪赌成功竟成神")
    private String content;

    @ApiModelProperty(value = "内容签名", name = "contentSignature", example = "SRC-ginna78XDJEKj--ln")
    private String contentSignature;

    @ApiModelProperty(value = "加密类型:0;1;2", name = "encryptType", example = "SRC-ginna78XDJEKj--ln")
    private Integer encryptType;

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-08-12 13:14:15")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "2019-08-12 13:14:15")
    private LocalDateTime updateTime;

}
