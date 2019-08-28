package com.xyz.caofancpu.trackingtime.service;


import com.xyz.caofancpu.trackingtime.model.Attachment;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 *
 */
public interface CommonOperateService {

    /**
     * 上传附件
     *
     * @param attachment
     * @param file
     * @throws GlobalErrorInfoException
     */
    void uploadAttachment(Attachment attachment, MultipartFile file)
            throws GlobalErrorInfoException;

    /**
     * 获取附件
     *
     * @param attachmentName
     * @return
     * @throws GlobalErrorInfoException
     */
    String getAttachmentAccessUrl(String attachmentName)
            throws GlobalErrorInfoException;

    void closeFileOperateLogging(Map<String, Object> paramMap);

    /**
     * 获取TOKEN
     *
     * @return
     */
    String loadToken();

    /**
     * 创建文件的父目录
     *
     * @param mainRoot
     */
    void createParentDir(String mainRoot);
}

