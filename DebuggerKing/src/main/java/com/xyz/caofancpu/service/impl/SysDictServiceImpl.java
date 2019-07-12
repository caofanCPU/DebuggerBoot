package com.xyz.caofancpu.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.caofancpu.mapper.SysDictMapper;
import com.xyz.caofancpu.service.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * ServiceImpl CommonOperate
 * This code is generated by the Hen, strongly recommended not to modify directly.
 */
@Service("sysDictService")
@DependsOn("initContextPropertyInitializer")
public class SysDictServiceImpl implements SysDictService {

    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(SysDictServiceImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private transient SysDictMapper sysDictMapper;

    @Override
    public List<Map<String, Object>> getInitSysDictList() {
        return sysDictMapper.getInitSysDictList();
    }

    @Override
    public PageInfo<List<Map<String, Object>>> getSysDictList() {
        PageHelper.startPage(1, 10);
        List<Map<String, Object>> resultList = sysDictMapper.getSysDictList();
        PageInfo<List<Map<String, Object>>> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }
}

