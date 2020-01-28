package com.xyz.caofancpu.trackingtime.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xyz.caofancpu.trackingtime.model.UserInfoMo;
import com.xyz.caofancpu.trackingtime.service.UserInfoService;
import com.xyz.caofancpu.trackingtime.view.UserInfoVo;
import com.xyz.caofancpu.util.result.D8API;
import com.xyz.caofancpu.util.result.D8Response;
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
 * UserInfoMo控制器
 *
 * @author Power+
 */
@RestController
@Api(tags = {"UserInfoMo模块接口"})
@ApiSort(0)
@Slf4j
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping(value = "/virtualCoin/add")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "UserInfoMo新增记录")
    public D8Response<Long> add(@Valid @RequestBody UserInfoVo userInfoVo) {
        // 转换数据
        UserInfoMo userInfoMo = JSONObject.parseObject(JSONObject.toJSONString(userInfoVo), UserInfoMo.class);
        userInfoService.add(userInfoMo);
        return D8API.success(userInfoMo.getId());
    }

    @PostMapping(value = "/virtualCoin/batchAdd")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "UserInfoMo批量新增")
    public D8Response<Integer> batchAdd(@Valid @RequestBody List<UserInfoVo> userInfoVoList) {
        List<UserInfoMo> userInfoMoList = new ArrayList<>(userInfoVoList.size());
        for (UserInfoVo userInfoVo : userInfoVoList) {
            userInfoMoList.add(JSONObject.parseObject(JSONObject.toJSONString(userInfoVo), UserInfoMo.class));
        }
        return D8API.success(userInfoService.batchAdd(userInfoMoList));
    }

    @PostMapping(value = "/virtualCoin/queryUserInfoMoList")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "UserInfoMo列表查询")
    public D8Response<List<UserInfoVo>> queryUserInfoMoList(@Valid @RequestBody UserInfoVo userInfoVo) {
        // 转换数据
        UserInfoMo userInfoMo = JSONObject.parseObject(JSONObject.toJSONString(userInfoVo), UserInfoMo.class);
        return D8API.success(userInfoService.queryUserInfoMoList(userInfoMo));
    }

    @PostMapping(value = "/virtualCoin/queryUserInfoMoPage")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "UserInfoMo分页查询")
    public D8Response<PageInfo<UserInfoVo>> queryUserInfoMoPage(@Valid @RequestBody UserInfoVo userInfoVo) {
        // 转换数据
        UserInfoMo userInfoMo = JSONObject.parseObject(JSONObject.toJSONString(userInfoVo), UserInfoMo.class);
        List<UserInfoVo> resultUserInfoMoList = userInfoService.queryUserInfoMoList(userInfoMo, userInfoVo.getPageNum(), userInfoVo.getPageSize());
        return D8API.success(PageInfo.of(resultUserInfoMoList));
    }

    @PostMapping(value = "/virtualCoin/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "UserInfoMo修改记录")
    public D8Response<Long> update(@Valid @RequestBody UserInfoVo userInfoVo) {
        // 转换数据
        UserInfoMo userInfoMo = JSONObject.parseObject(JSONObject.toJSONString(userInfoVo), UserInfoMo.class);
        userInfoService.updateSelectiveById(userInfoMo);
        return D8API.success(userInfoMo.getId());
    }

    @PostMapping(value = "/virtualCoin/batchUpdate")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "UserInfoMo批量修改记录")
    public D8Response<Integer> batchUpdate(@NotEmpty @RequestBody List<UserInfoVo> userInfoVoList) {
        // 转换数据
        List<UserInfoMo> userInfoList = new ArrayList<>(userInfoVoList.size());
        userInfoVoList.forEach(item -> userInfoList.add(JSONObject.parseObject(JSONObject.toJSONString(item), UserInfoMo.class)));
        return D8API.success(userInfoService.batchUpdateSelectiveById(userInfoList));
    }

    @PostMapping(value = "/virtualCoin/delete")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "UserInfoMo删除记录")
    public D8Response<Long> delete(@Valid @RequestBody UserInfoVo userInfoVo) {
        userInfoService.delete(userInfoVo.getId());
        return D8API.success(userInfoVo.getId());
    }

}
