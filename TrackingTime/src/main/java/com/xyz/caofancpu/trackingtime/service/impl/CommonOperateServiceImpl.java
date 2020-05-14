package com.xyz.caofancpu.trackingtime.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.core.DateUtil;
import com.xyz.caofancpu.mvc.common.GlobalResultCheckUtil;
import com.xyz.caofancpu.property.SpringConfigProperties;
import com.xyz.caofancpu.result.CustomerErrorInfo;
import com.xyz.caofancpu.result.D8Response;
import com.xyz.caofancpu.result.GlobalErrorInfoException;
import com.xyz.caofancpu.trackingtime.model.Attachment;
import com.xyz.caofancpu.trackingtime.service.CommonOperateService;
import com.xyz.caofancpu.trackingtime.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 *
 * @author caofanCPU
 */
@Service("commonOperateService")
@DependsOn("initContextPropertyInitializer")
@Slf4j
public class CommonOperateServiceImpl implements CommonOperateService {

    @Resource
    private RestTemplateUtil restTemplateUtil;

    @Resource
    private SpringConfigProperties springConfigProperties;

    @Override
    public void uploadAttachment(Attachment attachment, MultipartFile file)
            throws GlobalErrorInfoException {
        if (Objects.isNull(file) || file.isEmpty()) {
            throw new GlobalErrorInfoException(new CustomerErrorInfo("上传文件不能为空!"));
        }
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            log.error("获取文件二进制流失败, {}", e.getMessage());
            throw new GlobalErrorInfoException(new CustomerErrorInfo("文件上传失败, 请重试!"));
        }

        String originalFilename = file.getOriginalFilename().toLowerCase();
        String type = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 上传服务器开始
        String path = UUID.randomUUID().toString().replaceAll("-", "") + type;
        String fileBase64 = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> paramMap = new HashMap<String, Object>(8, 0.75f) {
            {
                put("appname", springConfigProperties.applicationName);
                put("filename", path);
                put("contents", fileBase64);
                put("open", false);
            }
        };
        // 禁用文件内容打印LOG
        closeFileOperateLogging(paramMap);
        restTemplateUtil.postBody(springConfigProperties.fileAccessUrl + "/file/upload", paramMap);

        attachment.setType(type);
        attachment.setName(path);
        attachment.setCreateTime(DateUtil.getCurrentTime(DateUtil.DATETIME_FORMAT_SIMPLE));

        log.info("\n客户端于[{}]上传文件：[{}]", attachment.getCreateTime(), attachment.getName());
    }

    @Override
    public String getAttachmentAccessUrl(String attachmentName)
            throws GlobalErrorInfoException {
        if (StringUtils.isBlank(attachmentName)) {
            throw new GlobalErrorInfoException(new CustomerErrorInfo("文件名参数错误"));
        }
        Map<String, Object> map = new HashMap<String, Object>(4, 0.5f) {
            {
                put("appname", springConfigProperties.applicationName);
                put("filename", attachmentName);
            }
        };

        D8Response d8Response = restTemplateUtil.postBody(springConfigProperties.fileAccessUrl + "/file/generateUrl", map);
        GlobalResultCheckUtil.handleResult(d8Response);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(d8Response));
        String accessUrl = jsonObject.getString("data");
        log.info("查询文件访问Url:\n输入文件名[{}]\n输出Url[{}]", attachmentName, accessUrl);
        return accessUrl;
    }

    @Override
    public String loadToken() {
        RequestAttributes temRequestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(temRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes sra = (ServletRequestAttributes) temRequestAttributes;
        RequestContextHolder.setRequestAttributes(sra, true);
        HttpServletRequest request;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.info("[提示]无HttpServletRequest信息, 加载token为null");
            return null;
        }
        // TODO
        String token = request.getHeader("TODO");
        return token;
    }

    @Override
    public void createParentDir(String mainRoot) {
        File file = new File(mainRoot);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public void closeFileOperateLogging(Map<String, Object> paramMap) {
        if (Objects.isNull(paramMap)) {
            return;
        }
        // TODO
    }

}

