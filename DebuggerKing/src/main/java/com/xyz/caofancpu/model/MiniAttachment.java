package com.xyz.caofancpu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * FileName: MiniAttachment
 * Author:   caofanCPU
 * Date:     2018/9/5 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniAttachment implements Serializable {

    private String filePath;
    private String busId;
    private String namePrefix;
}
