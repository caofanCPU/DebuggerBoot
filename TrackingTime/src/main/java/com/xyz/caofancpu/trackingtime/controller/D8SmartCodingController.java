package com.xyz.caofancpu.trackingtime.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xyz.caofancpu.trackingtime.model.D8SmartCodingMo;
import com.xyz.caofancpu.trackingtime.service.D8SmartCodingService;
import com.xyz.caofancpu.trackingtime.view.D8SmartCodingVo;
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
 * D8SmartCodingMo控制器
 *
 * @author Power+
 */
@RestController
@Api(tags = {"D8SmartCodingMo模块接口"})
@ApiSort(0)
@Slf4j
public class D8SmartCodingController {

    @Resource
    private D8SmartCodingService d8SmartCodingService;

    @PostMapping(value = "/virtualCoin/add")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "D8SmartCodingMo新增记录")
    public Object add(@Valid @RequestBody D8SmartCodingVo d8SmartCodingVo) {
        // 转换数据
        D8SmartCodingMo d8SmartCodingMo = JSONObject.parseObject(JSONObject.toJSONString(d8SmartCodingVo), D8SmartCodingMo.class);
        d8SmartCodingService.add(d8SmartCodingMo);
        return d8SmartCodingMo.getId();
    }

    @PostMapping(value = "/virtualCoin/batchAdd")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "D8SmartCodingMo批量新增")
    public Object batchAdd(@Valid @RequestBody List<D8SmartCodingVo> d8SmartCodingVoList) {
        List<D8SmartCodingMo> d8SmartCodingMoList = new ArrayList<>(d8SmartCodingVoList.size());
        for (D8SmartCodingVo d8SmartCodingVo : d8SmartCodingVoList) {
            d8SmartCodingMoList.add(JSONObject.parseObject(JSONObject.toJSONString(d8SmartCodingVo), D8SmartCodingMo.class));
        }
        return d8SmartCodingService.batchAdd(d8SmartCodingMoList);
    }

    @PostMapping(value = "/virtualCoin/queryD8SmartCodingMoList")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "D8SmartCodingMo列表查询")
    public Object queryD8SmartCodingMoList(@Valid @RequestBody D8SmartCodingVo d8SmartCodingVo) {
        // 转换数据
        D8SmartCodingMo d8SmartCodingMo = JSONObject.parseObject(JSONObject.toJSONString(d8SmartCodingVo), D8SmartCodingMo.class);
        return d8SmartCodingService.queryD8SmartCodingMoList(d8SmartCodingMo);
    }

    @PostMapping(value = "/virtualCoin/queryD8SmartCodingMoPage")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "D8SmartCodingMo分页查询")
    public Object queryD8SmartCodingMoPage(@Valid @RequestBody D8SmartCodingVo d8SmartCodingVo) {
        // 转换数据
        D8SmartCodingMo d8SmartCodingMo = JSONObject.parseObject(JSONObject.toJSONString(d8SmartCodingVo), D8SmartCodingMo.class);
        List<D8SmartCodingMo> resultD8SmartCodingMoList = d8SmartCodingService.queryD8SmartCodingMoList(d8SmartCodingMo, d8SmartCodingVo.getPageNum(), d8SmartCodingVo.getPageSize());
        return PageInfo.of(resultD8SmartCodingMoList);
    }

    @PostMapping(value = "/virtualCoin/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "D8SmartCodingMo修改记录")
    public Object update(@Valid @RequestBody D8SmartCodingVo d8SmartCodingVo) {
        // 转换数据
        D8SmartCodingMo d8SmartCodingMo = JSONObject.parseObject(JSONObject.toJSONString(d8SmartCodingVo), D8SmartCodingMo.class);
        return d8SmartCodingService.updateSelectiveById(d8SmartCodingMo);
    }

    @PostMapping(value = "/virtualCoin/batchUpdate")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "D8SmartCodingMo批量修改记录")
    public Object batchUpdate(@NotEmpty @RequestBody List<D8SmartCodingVo> d8SmartCodingVoList) {
        // 转换数据
        List<D8SmartCodingMo> d8SmartCodingList = new ArrayList<>(d8SmartCodingVoList.size());
        d8SmartCodingVoList.forEach(item -> d8SmartCodingList.add(JSONObject.parseObject(JSONObject.toJSONString(item), D8SmartCodingMo.class)));
        return d8SmartCodingService.batchUpdateSelectiveById(d8SmartCodingList);
    }

    @PostMapping(value = "/virtualCoin/delete")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "D8SmartCodingMo删除记录")
    public Object delete(@Valid @RequestBody D8SmartCodingVo d8SmartCodingVo) {
        return d8SmartCodingService.delete(d8SmartCodingVo.getId());
    }


}
