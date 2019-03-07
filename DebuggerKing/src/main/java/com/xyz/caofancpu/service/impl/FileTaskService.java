package com.xyz.caofancpu.service.impl;

import com.xyz.caofancpu.model.FileClassifiedResult;
import com.xyz.caofancpu.model.MiniAttachment;
import com.xyz.caofancpu.service.CommonOperateService;
import com.xyz.caofancpu.service.configValue.CommonConfigValueService;
import com.xyz.caofancpu.util.commonOperateUtils.FileBase64Util;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * FileName: FileTaskService
 * Author:   caofanCPU
 * Date:     2018/9/5 14:16
 */
@Service("fileTaskService")
@Api(description = "文件任务服务")
public class FileTaskService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileTaskService.class);
    
    // 引入默认线程池
    @Resource(name = "defaultThreadPool")
    private ThreadPoolTaskExecutor executor;
    
    @Autowired
    private CommonOperateService commonOperateService;
    
    @Autowired
    private CommonConfigValueService commonConfigValueService;
    
    public void copyFile(List<FileClassifiedResult> fileInfoList) {
        if (Objects.isNull(fileInfoList)) {
            return;
        }
        List<Future> futureList = new ArrayList<>();
        List<Future> completedTaskList = new ArrayList<>();
        Integer average = (int) Math.ceil(fileInfoList.size() * 1.0 / commonConfigValueService.taskHandleAverage);
        List<List<FileClassifiedResult>> groupedTaskList = balancedGroupingByTask(fileInfoList, average);
        if (CollectionUtils.isEmpty(groupedTaskList)) {
            return;
        }
        groupedTaskList.stream()
                .filter(Objects::nonNull)
                .forEach(item -> {
                    Future<?> future = executor.submit(() -> {
                        handleFileCopy(item);
                    });
                    futureList.add(future);
                });
        if (CollectionUtils.isNotEmpty(futureList)) {
            futureList.stream()
                    .filter(Objects::nonNull)
                    .forEach(future -> {
                        while (true) {
                            if (future.isDone() && !future.isCancelled()) {
                                logger.info("任务执行OK");
                                completedTaskList.add(future);
                                break;
                            }
                        }
                    });
        }
    }
    
    private void handleFileCopy(List<FileClassifiedResult> fileClassifiedResultList) {
        if (CollectionUtils.isEmpty(fileClassifiedResultList)) {
            return;
        }
        fileClassifiedResultList.stream()
                .filter(Objects::nonNull)
                .forEach(item -> {
                    // 1.创建目录
                    String destFileParentPath = commonConfigValueService.taskRootPath + commonConfigValueService.taskResultPath + item.getLocation();
                    commonOperateService.createParentDir(destFileParentPath);
                    // 2.
                    List<MiniAttachment> attachmentList = item.getAttachmentList();
                    if (CollectionUtils.isNotEmpty(attachmentList)) {
                        attachmentList.stream()
                                .filter(Objects::nonNull)
                                .forEach(mini -> {
                                    String sourceFileName = mini.getFilePath();
                                    if (Objects.nonNull(sourceFileName)) {
                                        String[] pathArr = sourceFileName.split("/");
                                        String fileSourceName = pathArr[pathArr.length - 1];
                                        if (Objects.nonNull(mini.getNamePrefix())) {
                                            fileSourceName = mini.getNamePrefix() + "_" + fileSourceName;
                                        }
                                        String destFileFullPath = destFileParentPath + "/" + fileSourceName;
                                        try {
                                            FileBase64Util.copyFile(mini.getFilePath(), destFileFullPath);
                                        } catch (IOException e) {
                                            logger.error("拷贝文件失败, 文件原名: [{}], {}", sourceFileName, e.getMessage());
                                            return;
                                        }
                                        logger.info("拷贝文件成功!", sourceFileName);
                                    }
                                });
                    }
                });
    }
    
    
    public <T> List<List<T>> balancedGroupingByTask(List<T> sourceList, Integer average) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<List<T>> resultList = new ArrayList<>();
        int remaider = sourceList.size() % average;
        int number = sourceList.size() / average;
        int offset = 0;//偏移量
        for (int i = 0; i < average; i++) {
            List<T> value;
            if (remaider > 0) {
                value = sourceList.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = sourceList.subList(i * number + offset, (i + 1) * number + offset);
            }
            resultList.add(value);
        }
        return resultList;
    }
    
}
