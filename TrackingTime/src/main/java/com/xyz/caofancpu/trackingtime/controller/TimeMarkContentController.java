package com.xyz.caofancpu.trackingtime.controller;

import com.xyz.caofancpu.trackingtime.constant.apiurls.AccessUrl;
import com.xyz.caofancpu.trackingtime.service.impl.TimeMarkContentService;
import com.xyz.caofancpu.trackingtime.view.TimeMarkContentVO;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(description = "TimeMarkContentController", tags = {"时间内容主体处理接口"})
@Slf4j
public class TimeMarkContentController {

    @Resource
    private transient TimeMarkContentService timeMarkContentService;

    @PostMapping(AccessUrl.TIME_MARK_SAVE)
    @ApiOperation(value = "保存内容", notes = "传参：timeBlockId, title, authorId")
    public ResultBody saveMarkContent(@RequestBody TimeMarkContentVO timeMarkContentVo)
            throws GlobalErrorInfoException {
        return new ResultBody(timeMarkContentService.saveMarkContent(timeMarkContentVo));
    }

    @PostMapping(AccessUrl.TIME_MARK_QUERY_LIST)
    @ApiOperation(value = "查询内容列表", notes = "传参：authorId, startTime, endTime")
    public ResultBody queryMarkContent(@RequestBody TimeMarkContentVO timeMarkContentVo)
            throws GlobalErrorInfoException {
        return new ResultBody(timeMarkContentService.queryList(timeMarkContentVo));
    }

    @PostMapping(AccessUrl.TIME_MARK_QUERY_DETAIL)
    @ApiOperation(value = "查询内容详情", notes = "传参：id, authorId")
    public ResultBody queryMarkDetail(@RequestBody TimeMarkContentVO timeMarkContentVo)
            throws GlobalErrorInfoException {
        return new ResultBody(timeMarkContentService.queryDetail(timeMarkContentVo));
    }

}
