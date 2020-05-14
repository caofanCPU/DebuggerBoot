package com.xyz.caofancpu.service.impl;

import com.xyz.caofancpu.core.CollectionUtil;
import com.xyz.caofancpu.core.FileUtil;
import com.xyz.caofancpu.model.FileClassifiedResult;
import com.xyz.caofancpu.model.MiniAttachment;
import com.xyz.caofancpu.multithreadutils.remote.RemoteInvokeHelper;
import com.xyz.caofancpu.multithreadutils.remote.RemoteRequestTask;
import com.xyz.caofancpu.result.D8Response;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 文件处理服务
 *
 * @author D8GER
 */
@Service("fileTaskService")
@Api(description = "文件任务服务")
@Slf4j
public class FileTaskService {

    @Resource(name = "businessThreadPool")
    private ThreadPoolTaskExecutor defaultThreadPool;

    public void copyFile(List<FileClassifiedResult> fileInfoList) {
        if (Objects.isNull(fileInfoList)) {
            return;
        }
        List<Future> futureList = new ArrayList<>();
        List<Future> completedTaskList = new ArrayList<>();
        Integer average = (int) Math.ceil(fileInfoList.size() * 1.0 / 10);
        List<List<FileClassifiedResult>> groupedTaskList = balancedGroupingByTask(fileInfoList, average);
        if (CollectionUtil.isEmpty(groupedTaskList)) {
            return;
        }
        groupedTaskList.stream()
                .filter(Objects::nonNull)
                .forEach(item -> {
                    Future<?> future = defaultThreadPool.submit(() -> handleFileCopy(item));
                    futureList.add(future);
                });
        if (CollectionUtil.isNotEmpty(futureList)) {
            futureList.stream()
                    .filter(Objects::nonNull)
                    .forEach(future -> {
                        while (true) {
                            if (future.isDone() && !future.isCancelled()) {
                                log.info("任务执行OK");
                                completedTaskList.add(future);
                                break;
                            }
                        }
                    });
        }
    }

    private void handleFileCopy(List<FileClassifiedResult> fileClassifiedResultList) {
        if (CollectionUtil.isEmpty(fileClassifiedResultList)) {
            return;
        }
        fileClassifiedResultList.stream()
                .filter(Objects::nonNull)
                .forEach(item -> {
                    // 1.创建目录
                    String destFileParentPath = "/work/www" + "/haha" + item.getLocation();
                    // 2.
                    List<MiniAttachment> attachmentList = item.getAttachmentList();
                    if (CollectionUtil.isNotEmpty(attachmentList)) {
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
                                            FileUtil.copyFile(mini.getFilePath(), destFileFullPath);
                                        } catch (IOException e) {
                                            log.error("拷贝文件失败, 文件原名: [{}], {}", sourceFileName, e.getMessage());
                                            return;
                                        }
                                        log.info("拷贝文件成功!", sourceFileName);
                                    }
                                });
                    }
                });
    }


    public <T> List<List<T>> balancedGroupingByTask(List<T> sourceList, Integer average) {
        if (CollectionUtil.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<List<T>> resultList = new ArrayList<>();
        int remain = sourceList.size() % average;
        int number = sourceList.size() / average;
        // 偏移量
        int offset = 0;
        for (int i = 0; i < average; i++) {
            List<T> value;
            if (remain > 0) {
                value = sourceList.subList(i * number + offset, (i + 1) * number + offset + 1);
                remain--;
                offset++;
            } else {
                value = sourceList.subList(i * number + offset, (i + 1) * number + offset);
            }
            resultList.add(value);
        }
        return resultList;
    }

    public D8Response execute() {
        Map<Integer, Future<?>> taskResultMap = new HashMap<>();
        addToPool(taskResultMap, 1, this, "loadData", 1, new Class[]{Integer.class});
        addToPool(taskResultMap, 2, this, "loadData", 2, new Class[]{Integer.class});
        addToPool(taskResultMap, 3, this, "loadData", 3, new Class[]{Integer.class});

        int[] checkResultArray = new int[]{0, 0, 0};
        taskResultMap.keySet().stream()
                .filter(Objects::nonNull)
                .forEach(key -> {
                    Future<?> currentFuture = taskResultMap.get(key);
                    switch (key) {
                        case 1:
                            // check phone
                            if (checkResult(currentFuture)) {
                                checkResultArray[0] = 1;
                            }
                            break;
                        case 2:
                            // check cardId
                            if (checkResult(currentFuture)) {
                                checkResultArray[1] = 1;
                            }
                            break;
                        case 3:
                            // check bankCardId
                            if (checkResult(currentFuture)) {
                                checkResultArray[2] = 1;
                            }
                            break;
                        default:
                            break;
                    }
                });
        return getCheckResult(checkResultArray);
    }

    private D8Response getCheckResult(int[] checkResultArray) {
        if (checkResultArray[0] != 1) {
            return D8Response.fail("手机号验证失败！");
        }
        if (checkResultArray[1] != 1) {
            return D8Response.fail("身份证号验证失败！");
        }
        if (checkResultArray[2] != 1) {
            return D8Response.fail("银行卡号验证失败！");
        }
        return D8Response.fail(StringUtils.EMPTY);
    }

    private boolean checkResult(Future task) {
        try {
            return (Boolean) task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    public Integer loadData(Integer id) {
        return 1;
    }

    private void addToPool(Map<Integer, Future<?>> taskResultMap, Integer taskNo, Object executeService, String executeMethod, Object params, Class<?>... paramClass) {
        RemoteInvokeHelper remoteService = new RemoteInvokeHelper(executeService, executeMethod, params, paramClass);
        RemoteRequestTask task = new RemoteRequestTask(remoteService);
        taskResultMap.put(taskNo, defaultThreadPool.submit(task));
    }

}
