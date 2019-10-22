package com.xyz.caofancpu.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xyz.caofancpu.model.Attachment;
import com.xyz.caofancpu.model.SysConfigDictMo;
import com.xyz.caofancpu.service.CommonOperateService;
import com.xyz.caofancpu.service.SysDictService;
import com.xyz.caofancpu.util.commonOperateUtils.HttpStaticHandleUtil;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Api(description = "CommonOperateController", tags = {"公共处理接口"})
@Slf4j
public class CommonOperateController {
    @Resource
    private transient CommonOperateService commonOperateService;

    @Resource(type = SysDictService.class)
    private transient SysDictService sysDictService;

    @PostMapping("/sysDict/listByPage")
    @ApiOperation(value = "获取系统配置列表", notes = "传参：无")
    public ResultBody listSysDictByPage()
            throws GlobalErrorInfoException {
        PageInfo<List<SysConfigDictMo>> resultPageInfo = sysDictService.getSysDictList();
        return new ResultBody(resultPageInfo);
    }

    @PostMapping("/sysDict/testBatchInsertWithBackIds")
    @ApiOperation(value = "测试批量插入返回ID", notes = "传参：无")
    public ResultBody testBatchInsertWithBackIds()
            throws GlobalErrorInfoException {
        List<SysConfigDictMo> sysConfigDictMoList = Lists.newArrayList(
                new SysConfigDictMo().setPid(0).setName("配置1").setCode("zopi0").setSortId(1),
                new SysConfigDictMo().setPid(0).setName("配置2").setCode("IG666").setSortId(2)
        );
        sysDictService.batchAddConfig(sysConfigDictMoList);
        return new ResultBody(sysConfigDictMoList);
    }

    /**
     * 上传附件
     *
     * @param file
     * @param request
     * @return
     * @throws GlobalErrorInfoException
     */
    @Deprecated
    @PostMapping("/attachment/upload")
    public ResultBody uploadAttachment(MultipartFile file, HttpServletRequest request)
            throws GlobalErrorInfoException {
        Map<String, Object> requestParamMap = HttpStaticHandleUtil.getParameterMap(request);
        Attachment attachment = JSONUtil.copyProperties(requestParamMap, Attachment.class);
        commonOperateService.uploadAttachment(attachment, file);
        return new ResultBody(attachment);
    }

    /**
     * 根据附件名称获取访问url
     *
     * @param attachmentName
     * @return
     * @throws GlobalErrorInfoException
     */
    @Deprecated
    @PostMapping("/attachment/getAccessUrl")
    public ResultBody getAccessUrl(@RequestParam(required = true) String attachmentName)
            throws GlobalErrorInfoException {
        String accessUrl = commonOperateService.getAttachmentAccessUrl(attachmentName);
        return new ResultBody(accessUrl);
    }
}
