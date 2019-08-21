package com.xyz.caofancpu.controller;

import com.github.pagehelper.PageInfo;
import com.xyz.caofancpu.model.Attachment;
import com.xyz.caofancpu.service.CommonOperateService;
import com.xyz.caofancpu.service.SysDictService;
import com.xyz.caofancpu.util.result.GlobalErrorInfoEnum;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import com.xyz.caofancpu.utils.DataHelper;
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
        PageInfo<List<Map<String, Object>>> resultPageInfo = sysDictService.getSysDictList();
        return new ResultBody(resultPageInfo);
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
        Attachment attachment = new Attachment();
        Map<String, Object> map = DataHelper.getParameterMap(request);
        try {
            DataHelper.putDataIntoEntity(map, attachment);
        } catch (Exception e) {
            log.error("MAP转换到Attachment出错: \n", e);
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.INTERNAL_ERROR);
        }
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
