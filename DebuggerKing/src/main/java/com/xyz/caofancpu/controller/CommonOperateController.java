package com.xyz.caofancpu.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xyz.caofancpu.model.Attachment;
import com.xyz.caofancpu.model.SysConfigDictMo;
import com.xyz.caofancpu.mvc.config.CommonConfigValueService;
import com.xyz.caofancpu.service.CommonOperateService;
import com.xyz.caofancpu.service.SysDictService;
import com.xyz.caofancpu.util.commonoperateutils.HttpStaticHandleUtil;
import com.xyz.caofancpu.util.dataoperateutils.JSONUtil;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import com.xyz.caofancpu.util.streamoperateutils.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    @Autowired
    private transient CommonConfigValueService commonConfigValueService;

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

    /**
     * 文件下载, 支持GET/POST
     * 当前根据文件名下载, 后期可优化为根据文件KEY下载
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<byte[]> download(@RequestParam("fileName") String fileName)
            throws Exception {
        File file = new File(commonConfigValueService.localOSSUploadRoot + File.separator + fileName);
        String downloadFile = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 以下载方式打开文件
        headers.setContentDispositionFormData("attachment", downloadFile);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }

    /**
     * 多文件上传, 注意上传文件大小限制配置
     *
     * @param multiUploadRequest
     * @return
     */
    @PostMapping("/multiUpload")
    public ResultBody multiUpload(MultipartHttpServletRequest multiUploadRequest) {
        List<MultipartFile> uploadFileList = multiUploadRequest.getFiles("file");
        if (CollectionUtil.isEmpty(uploadFileList)) {
            throw new RuntimeException("请选择上传文件!");
        }
        List<String> fileNameList = Lists.newArrayList();
        for (int i = 0; i < uploadFileList.size(); i++) {
            MultipartFile uploadFile = uploadFileList.get(i);
            String fileName = uploadFile.getOriginalFilename();
            if (uploadFile.isEmpty()) {
                throw new RuntimeException("第" + (i + 1) + "个文件: [" + fileName + "]为空, 请检查!");
            }
            try {
                uploadFile.transferTo(new File(commonConfigValueService.localOSSUploadRoot + File.separator + fileName));
                fileNameList.add(fileName);
            } catch (IOException e) {
                log.error("上传文件出现异常", e);
                throw new RuntimeException("上传文件失败, 请重试");
            }
        }
        return new ResultBody(fileNameList);
    }
}
