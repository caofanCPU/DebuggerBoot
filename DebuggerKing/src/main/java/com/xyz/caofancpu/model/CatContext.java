package com.xyz.caofancpu.model;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName: CatContext
 *
 * @author: caofanCPU
 * @date: 2019/1/24 15:20
 */
public class CatContext implements Cat.Context {
    private Map<String, String> map = new HashMap<>();
    
    @Override
    public void addProperty(String key, String value) {
        map.put(key, value);
    }
    
    @Override
    public String getProperty(String key) {
        return map.get(key);
    }
}
