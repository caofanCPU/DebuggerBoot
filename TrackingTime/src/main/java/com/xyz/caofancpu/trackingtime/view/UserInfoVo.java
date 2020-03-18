package com.xyz.caofancpu.trackingtime.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * UserInfoMo对应的SwaggerApi增强Vo对象
 *
 * @author Power+
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel
public class UserInfoVo {

    @ApiModelProperty(value = "ID 用户唯一编码", required = false, example = "", position = 1)
    private Long id;

    @ApiModelProperty(value = "name 姓名", required = false, example = "", position = 2)
    private String name;

    @ApiModelProperty(value = "age 年龄", required = false, example = "", position = 3)
    private Integer age;

    @ApiModelProperty(value = "job 工作", required = false, example = "", position = 4)
    private String job;

    @ApiModelProperty(value = "pageNum", required = false, example = "1", position = 5)
    private Integer pageNum;

    @ApiModelProperty(value = "pageSize", required = false, example = "10", position = 6)
    private Integer pageSize;

}