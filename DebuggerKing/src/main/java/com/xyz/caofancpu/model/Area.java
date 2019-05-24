package com.xyz.caofancpu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author CY_XYZ
 * @date 2018/7/29
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    
    private Integer id;
    
    private String name;
    
    private Integer pid;
    
    private List<Area> children;
}
