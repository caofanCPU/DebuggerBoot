package com.xyz.caofancpu.trackingtime.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xyz.caofancpu.trackingtime.model.D8gerMo;
import com.xyz.caofancpu.trackingtime.service.impl.D8gerHandler;
import com.xyz.caofancpu.trackingtime.view.D8gerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * D8gerMo控制器
 *
 * @author 帝八哥
 */
@RestController
@Api(tags = {"D8gerMo模块接口"})
@ApiSort(0)
@Slf4j
public class D8gerController {

    @Resource
    private D8gerHandler d8gerHandler;

    @PostMapping(value = "/d8gerAutoCode/d8gerMo/add")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "D8gerMo新增记录")
    public Object add(@Valid @RequestBody D8gerVo d8gerVo) {
        // 转换数据
        D8gerMo d8gerMo = JSONObject.parseObject(JSONObject.toJSONString(d8gerVo), D8gerMo.class);
        d8gerHandler.add(d8gerMo);
        return d8gerMo.getId();
    }

    @PostMapping(value = "/d8gerAutoCode/d8gerMo/batchAdd")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "D8gerMo批量新增")
    public Object batchAdd(@Valid @RequestBody List<D8gerVo> d8gerVoList) {
        List<D8gerMo> d8gerMoList = new ArrayList<>(d8gerVoList.size());
        for (D8gerVo d8gerVo : d8gerVoList) {
            d8gerMoList.add(JSONObject.parseObject(JSONObject.toJSONString(d8gerVo), D8gerMo.class));
        }
        return d8gerHandler.batchAdd(d8gerMoList);
    }

    @PostMapping(value = "/d8gerAutoCode/d8gerMo/queryD8gerMoList")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "D8gerMo列表查询")
    public Object queryD8gerMoList(@Valid @RequestBody D8gerVo d8gerVo) {
        // 转换数据
        D8gerMo d8gerMo = JSONObject.parseObject(JSONObject.toJSONString(d8gerVo), D8gerMo.class);
        return d8gerHandler.queryD8gerMoList(d8gerMo);
    }

    @PostMapping(value = "/d8gerAutoCode/d8gerMo/queryD8gerMoPage")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "D8gerMo分页查询")
    public Object queryD8gerMoPage(@Valid @RequestBody D8gerVo d8gerVo) {
        // 转换数据
        D8gerMo d8gerMo = JSONObject.parseObject(JSONObject.toJSONString(d8gerVo), D8gerMo.class);
        List<D8gerMo> resultD8gerMoList = d8gerHandler.queryD8gerMoList(d8gerMo, d8gerVo.getPageNum(), d8gerVo.getPageSize());
        return PageInfo.of(resultD8gerMoList);
    }

    @PostMapping(value = "/d8gerAutoCode/d8gerMo/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "D8gerMo修改记录")
    public Object update(@Valid @RequestBody D8gerVo d8gerVo) {
        // 转换数据
        D8gerMo d8gerMo = JSONObject.parseObject(JSONObject.toJSONString(d8gerVo), D8gerMo.class);
        return d8gerHandler.updateSelectiveById(d8gerMo);
    }

    @PostMapping(value = "/d8gerAutoCode/d8gerMo/batchUpdate")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "D8gerMo批量修改记录")
    public Object batchUpdate(@NotEmpty @RequestBody List<D8gerVo> d8gerVoList) {
        // 转换数据
        List<D8gerMo> d8gerList = new ArrayList<>(d8gerVoList.size());
        d8gerVoList.forEach(item -> d8gerList.add(JSONObject.parseObject(JSONObject.toJSONString(item), D8gerMo.class)));
        return d8gerHandler.batchUpdateSelectiveById(d8gerList);
    }

    @PostMapping(value = "/d8gerAutoCode/d8gerMo/delete")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "D8gerMo删除记录")
    public Object delete(@Valid @RequestBody D8gerVo d8gerVo) {
        return d8gerHandler.delete(d8gerVo.getId());
    }


}
