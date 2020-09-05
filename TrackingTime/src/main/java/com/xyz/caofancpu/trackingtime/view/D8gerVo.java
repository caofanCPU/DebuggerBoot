package com.xyz.caofancpu.trackingtime.view;

import com.xyz.caofancpu.trackingtime.constant.ActiveStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * D8gerMo对应的SwaggerApi增强Vo对象
 *
 * @author 帝八哥
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel
public class D8gerVo {

    @ApiModelProperty(value = "ID", required = false, example = "", position = 1)
    private Long id;

    @ApiModelProperty(value = "姓名", required = false, example = "", position = 2)
    private String name;

    @ApiModelProperty(value = "年龄", required = false, example = "", position = 3)
    private Integer age;

    @ApiModelProperty(value = "工作", required = false, example = "", position = 4)
    private String job;

    @ApiModelProperty(value = "激活状态", required = false, example = "", position = 5)
    private ActiveStatusEnum activeStatus;

    @ApiModelProperty(value = "创建时间", required = false, example = "", position = 6)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", required = false, example = "", position = 7)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "pageNum", required = false, example = "1", position = 8)
    private Integer pageNum;

    @ApiModelProperty(value = "pageSize", required = false, example = "10", position = 9)
    private Integer pageSize;

}