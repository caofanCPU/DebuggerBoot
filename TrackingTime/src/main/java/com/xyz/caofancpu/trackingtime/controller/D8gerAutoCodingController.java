package com.xyz.caofancpu.trackingtime.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xyz.caofancpu.trackingtime.model.D8gerAutoCodingMo;
import com.xyz.caofancpu.trackingtime.service.D8gerAutoCodingService;
import com.xyz.caofancpu.trackingtime.view.D8gerAutoCodingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * D8gerAutoCodingMo控制器
 *
 * @author 曹繁
 */
@RestController
@Api(tags = {"D8gerAutoCodingMo模块接口"})
@ApiSort(0)
@Slf4j
public class D8gerAutoCodingController {

    @Resource
    private D8gerAutoCodingService d8gerAutoCodingService;

    @PostMapping(value = "/api/api/v100/live/add")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "D8gerAutoCodingMo新增记录")
    public Object add(@Valid @RequestBody D8gerAutoCodingVo d8gerAutoCodingVo) {
        // 转换数据
        D8gerAutoCodingMo d8gerAutoCodingMo = JSONObject.parseObject(JSONObject.toJSONString(d8gerAutoCodingVo), D8gerAutoCodingMo.class);
        d8gerAutoCodingService.add(d8gerAutoCodingMo);
        return d8gerAutoCodingMo.getId();
    }

    @PostMapping(value = "/api/api/v100/live/batchAdd")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "D8gerAutoCodingMo批量新增")
    public Object batchAdd(@Valid @RequestBody List<D8gerAutoCodingVo> d8gerAutoCodingVoList) {
        List<D8gerAutoCodingMo> d8gerAutoCodingMoList = new ArrayList<>(d8gerAutoCodingVoList.size());
        for (D8gerAutoCodingVo d8gerAutoCodingVo : d8gerAutoCodingVoList) {
            d8gerAutoCodingMoList.add(JSONObject.parseObject(JSONObject.toJSONString(d8gerAutoCodingVo), D8gerAutoCodingMo.class));
        }
        return d8gerAutoCodingService.batchAdd(d8gerAutoCodingMoList);
    }

    @PostMapping(value = "/api/api/v100/live/queryD8gerAutoCodingMoList")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "D8gerAutoCodingMo列表查询")
    public Object queryD8gerAutoCodingMoList(@Valid @RequestBody D8gerAutoCodingVo d8gerAutoCodingVo) {
        // 转换数据
        D8gerAutoCodingMo d8gerAutoCodingMo = JSONObject.parseObject(JSONObject.toJSONString(d8gerAutoCodingVo), D8gerAutoCodingMo.class);
        return d8gerAutoCodingService.queryD8gerAutoCodingMoList(d8gerAutoCodingMo);
    }

    @PostMapping(value = "/api/api/v100/live/queryD8gerAutoCodingMoPage")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "D8gerAutoCodingMo分页查询")
    public Object queryD8gerAutoCodingMoPage(@Valid @RequestBody D8gerAutoCodingVo d8gerAutoCodingVo) {
        // 转换数据
        D8gerAutoCodingMo d8gerAutoCodingMo = JSONObject.parseObject(JSONObject.toJSONString(d8gerAutoCodingVo), D8gerAutoCodingMo.class);
        List<D8gerAutoCodingMo> resultD8gerAutoCodingMoList = d8gerAutoCodingService.queryD8gerAutoCodingMoList(d8gerAutoCodingMo, d8gerAutoCodingVo.getPageNum(), d8gerAutoCodingVo.getPageSize());
        return PageInfo.of(resultD8gerAutoCodingMoList);
    }

    @PostMapping(value = "/api/api/v100/live/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "D8gerAutoCodingMo修改记录")
    public Object update(@Valid @RequestBody D8gerAutoCodingVo d8gerAutoCodingVo) {
        // 转换数据
        D8gerAutoCodingMo d8gerAutoCodingMo = JSONObject.parseObject(JSONObject.toJSONString(d8gerAutoCodingVo), D8gerAutoCodingMo.class);
        return d8gerAutoCodingService.updateSelectiveById(d8gerAutoCodingMo);
    }

    @PostMapping(value = "/api/api/v100/live/delete")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "D8gerAutoCodingMo删除记录")
    public Object delete(@Valid @RequestBody D8gerAutoCodingVo d8gerAutoCodingVo) {
        return d8gerAutoCodingService.delete(d8gerAutoCodingVo.getId());
    }


}
