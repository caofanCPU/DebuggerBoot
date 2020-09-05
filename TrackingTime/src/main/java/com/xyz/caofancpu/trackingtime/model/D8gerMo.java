package com.xyz.caofancpu.trackingtime.model;

import com.xyz.caofancpu.trackingtime.constant.ActiveStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
     * ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 工作
     */
    private String job;

    /**
     * 激活状态
     */
    private ActiveStatusEnum activeStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}