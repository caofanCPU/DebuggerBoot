package com.xyz.caofancpu.springdata.model;

import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by caofanCPU on 2018/7/2.
 * 省市区
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class District implements Serializable {
    /**
     *
     */
    @NonNull
    @Id
    private int id;
    /**
     *
     */
    @NonNull
    private String name;
    /**
     *
     */
    @NonNull
    private int pid;
    /**
     *
     */
    private String initial;
    /**
     *
     */
    private String initials;
    /**
     *
     */
    private String pinyin;
    /**
     *
     */
    private String code;
    /**
     *
     */
    private String areaCode;
    /**
     *
     */
    private int sort;
    
}
