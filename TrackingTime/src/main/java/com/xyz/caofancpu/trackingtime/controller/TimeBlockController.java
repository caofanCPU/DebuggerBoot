package com.xyz.caofancpu.trackingtime.controller;

import com.xyz.caofancpu.trackingtime.constant.apiUrls.AccessUrl;
import com.xyz.caofancpu.trackingtime.model.TimeBlock;
import com.xyz.caofancpu.trackingtime.service.impl.TimeBlockService;
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
@Api(description = "TimeBlockController", tags = {"时间区块处理接口"})
@Slf4j
public class TimeBlockController {

    @Resource
    private transient TimeBlockService timeBlockService;

    @PostMapping(AccessUrl.TIME_BLACK_APPLY_ID)
    @ApiOperation(value = "获取时间区块ID", notes = "传参：userId, startTime, endTime")
    public ResultBody applyTimeBlockId(@RequestBody TimeBlock timeBlock)
            throws GlobalErrorInfoException {
        return new ResultBody(timeBlock);
    }


}
