package com.xyz.caofancpu.trackingtime.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xyz.caofancpu.trackingtime.constant.apiurls.AccessUrl;
import com.xyz.caofancpu.trackingtime.model.VirtualCoinTransactionRecord;
import com.xyz.caofancpu.trackingtime.service.VirtualCoinTransactionRecordService;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author caofanCPU
 */
@RestController
@Api(tags = {"虚拟货币交易记录处理接口"})
@ApiSort(1)
@Slf4j
public class VirtualCoinTransactionRecordController {

    @Resource
    private transient VirtualCoinTransactionRecordService virtualCoinTransactionRecordService;

    @PostMapping(AccessUrl.VIRTUAL_COIN_TRANSACTION_SAVE)
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "添加虚拟货币交易记录")
    public ResultBody addVirtualCoinTransaction(@RequestBody VirtualCoinTransactionRecord record)
            throws GlobalErrorInfoException {
        virtualCoinTransactionRecordService.insert(record);
        return new ResultBody();
    }

    @PostMapping(AccessUrl.VIRTUAL_COIN_TRANSACTION_QUERY_LIST)
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "查询虚拟货币交易记录列表")
    public ResultBody queryVirtualCoinTransactionList(@RequestBody VirtualCoinTransactionRecord record)
            throws GlobalErrorInfoException {
        List<VirtualCoinTransactionRecord> resultList = virtualCoinTransactionRecordService.select(record);
        return new ResultBody(resultList);
    }

}
