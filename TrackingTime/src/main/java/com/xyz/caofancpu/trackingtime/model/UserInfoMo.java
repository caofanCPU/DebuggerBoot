package com.xyz.caofancpu.trackingtime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * UserInfoMo
 *
 * @author Power+
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoMo {

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

}