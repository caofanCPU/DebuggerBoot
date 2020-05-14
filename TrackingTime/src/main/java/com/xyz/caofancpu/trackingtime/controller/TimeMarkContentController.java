package com.xyz.caofancpu.trackingtime.controller;

import com.xyz.caofancpu.result.D8Response;
import com.xyz.caofancpu.result.GlobalErrorInfoException;
import com.xyz.caofancpu.trackingtime.constant.apiurls.AccessUrl;
import com.xyz.caofancpu.trackingtime.service.impl.TimeMarkContentService;
import com.xyz.caofancpu.trackingtime.view.TimeMarkContentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = {"时间内容主体处理接口"})
@Slf4j
public class TimeMarkContentController {

    @Resource
    private transient TimeMarkContentService timeMarkContentService;

    @PostMapping(AccessUrl.TIME_MARK_SAVE)
    @ApiOperation(value = "保存内容", notes = "传参：timeBlockId, title, authorId")
    public D8Response<TimeMarkContentVO> saveMarkContent(@RequestBody TimeMarkContentVO timeMarkContentVo)
            throws GlobalErrorInfoException {
        return D8Response.success(timeMarkContentService.saveMarkContent(timeMarkContentVo));
    }

    @PostMapping(AccessUrl.TIME_MARK_QUERY_LIST)
    @ApiOperation(value = "查询内容列表", notes = "传参：authorId, startTime, endTime")
    public D8Response<List<TimeMarkContentVO>> queryMarkContent(@RequestBody TimeMarkContentVO timeMarkContentVo)
            throws GlobalErrorInfoException {
        return D8Response.success(timeMarkContentService.queryList(timeMarkContentVo));
    }

    @PostMapping(AccessUrl.TIME_MARK_QUERY_DETAIL)
    @ApiOperation(value = "查询内容详情", notes = "传参：id, authorId")
    public D8Response<TimeMarkContentVO> queryMarkDetail(@RequestBody TimeMarkContentVO timeMarkContentVo)
            throws GlobalErrorInfoException {
        return D8Response.success(timeMarkContentService.queryDetail(timeMarkContentVo));
    }

}
