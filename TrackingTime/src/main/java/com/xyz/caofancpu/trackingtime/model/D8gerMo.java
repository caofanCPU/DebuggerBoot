package com.xyz.caofancpu.trackingtime.model;

import com.xyz.caofancpu.trackingtime.constant.ActiveStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * D8gerMo
 *
 * @author 帝八哥
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class D8gerMo {

    /**
     * ID 用户唯一编码
     */
    private Long id;

    /**
     * name 姓名
     */
    private String name;

    /**
     * age 年龄
     */
    private Integer age;

    /**
     * job 工作
     */
    private String job;

    /**
     * 激活状态
     */
    private ActiveStatusEnum activeStatus;

}