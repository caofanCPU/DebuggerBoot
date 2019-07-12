package com.xyz.caofancpu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by caofanCPU on 2018/7/2.
 * 省市区
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class District implements Serializable, Cloneable {

    private Integer id;

    private String name;

    private Integer pid;

    private String initial;

    private String initials;

    private String pinyin;

    private String code;

    private String areaCode;

    private Integer sort;
}
