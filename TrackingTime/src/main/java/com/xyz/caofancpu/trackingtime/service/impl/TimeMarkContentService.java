package com.xyz.caofancpu.trackingtime.service.impl;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.mvc.annotation.Check;
import com.xyz.caofancpu.trackingtime.constant.enums.MarkContentStatusEnum;
import com.xyz.caofancpu.trackingtime.mapper.TimeBlockMapper;
import com.xyz.caofancpu.trackingtime.mapper.TimeMarkContentMapper;
import com.xyz.caofancpu.trackingtime.mapper.TimeMarkDetailMapper;
import com.xyz.caofancpu.trackingtime.model.TimeBlock;
import com.xyz.caofancpu.trackingtime.model.TimeMarkContent;
import com.xyz.caofancpu.trackingtime.model.TimeMarkDetail;
import com.xyz.caofancpu.trackingtime.view.TimeMarkContentVO;
import com.xyz.caofancpu.util.dataoperateutils.JSONUtil;
import com.xyz.caofancpu.util.result.GlobalErrorInfoEnum;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.streamoperateutils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class TimeMarkContentService {

    @Resource
    private TimeMarkContentMapper timeMarkContentMapper;

    @Resource
    private TimeMarkDetailMapper timeMarkDetailMapper;

    @Resource
    private TimeBlockMapper timeBlockMapper;

    public int batchInsert(List<TimeMarkContent> timeMarkContentList) {
        return timeMarkContentMapper.insertList(timeMarkContentList);
    }

    @Check({"authorId not empty:用户ID不能为空"})
    public List<TimeMarkContentVO> queryList(TimeMarkContentVO timeMarkContentVo) {
        TimeBlock queryBlock = new TimeBlock().setUserId(timeMarkContentVo.getAuthorId());
        if (Objects.nonNull(timeMarkContentVo.getStartTime())) {
            queryBlock.setStartTime(timeMarkContentVo.getStartTime());
        }
        if (Objects.nonNull(timeMarkContentVo.getEndTime())) {
            queryBlock.setEndTime(timeMarkContentVo.getEndTime());
        }
        List<TimeBlock> timeBlockList = timeBlockMapper.select(queryBlock);
        if (CollectionUtil.isEmpty(timeBlockList)) {
            return Lists.newArrayList();
        }
        List<TimeMarkContent> markContentList = CollectionUtil.transToListWithFlatMap(timeBlockList, timeBlock -> {
            TimeMarkContent queryContent = new TimeMarkContent()
                    .setAuthorId(timeMarkContentVo.getAuthorId())
                    .setTimeBlockId(timeBlock.getId());
            return timeMarkContentMapper.select(queryContent);
        });
        Map<Long, TimeBlock> timeBlockIdMap = CollectionUtil.transToMap(timeBlockList, TimeBlock::getId);
        return CollectionUtil.transToList(markContentList, timeMarkContent -> {
            TimeBlock timeBlock = timeBlockIdMap.get(timeMarkContent.getTimeBlockId());
            return JSONUtil.copyProperties(timeMarkContent, TimeMarkContentVO.class)
                    .setStartTime(timeBlock.getStartTime())
                    .setEndTime(timeBlock.getEndTime());
        });
    }

    @Check({"id not empty:ID参数非法", "authorId not empty:用户ID不能为空"})
    public TimeMarkContentVO queryDetail(TimeMarkContentVO timeMarkContentVo)
            throws GlobalErrorInfoException {
        TimeMarkContent queryContent = new TimeMarkContent()
                .setId(timeMarkContentVo.getId())
                .setAuthorId(timeMarkContentVo.getAuthorId());
        TimeMarkContent firstContent = CollectionUtil.findFirst(timeMarkContentMapper.select(queryContent), timeMarkContent -> timeMarkContent.getId().equals(queryContent.getId()));
        if (Objects.isNull(firstContent)) {
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.NOT_FOUND);
        }

        TimeMarkDetail queryDetail = new TimeMarkDetail().setMarkContentId(firstContent.getId());
        TimeMarkDetail firstDetail = CollectionUtil.findFirst(timeMarkDetailMapper.select(queryDetail), timeMarkDetail -> timeMarkDetail.getMarkContentId().equals(queryDetail.getMarkContentId()));

        TimeBlock queryBlock = new TimeBlock().setId(firstContent.getTimeBlockId());
        TimeBlock firstBlock = CollectionUtil.findFirst(timeBlockMapper.select(queryBlock), timeBlock -> timeBlock.getId().equals(queryBlock.getId()));

        return JSONUtil.copyProperties(firstContent, TimeMarkContentVO.class)
                .setContent(firstDetail.getContent())
                .setContentSignature(firstDetail.getContentSignature())
                .setEncryptType(firstDetail.getEncryptType())
                .setStartTime(firstBlock.getStartTime())
                .setEndTime(firstBlock.getEndTime());
    }

    public int update(TimeMarkContent timeMarkContent) {
        return timeMarkContentMapper.update(timeMarkContent);
    }

    @Check({"authorId not empty:用户ID不能为空", "timeBlockId not empty:时间区块ID不能为空",
            "title not empty:内容标题不能为空", "content not empty:内容不能为空"
    })
    @Transactional(rollbackFor = Throwable.class)
    public TimeMarkContentVO saveMarkContent(TimeMarkContentVO timeMarkContentVo)
            throws GlobalErrorInfoException {
        TimeBlock queryBlock = new TimeBlock().setId(timeMarkContentVo.getTimeBlockId());
        TimeBlock firstBlock = CollectionUtil.findFirst(timeBlockMapper.select(queryBlock), timeBlock -> timeBlock.getId().equals(queryBlock.getId()));

        if (Objects.isNull(firstBlock)) {
            throw new GlobalErrorInfoException("时间区块ID参数非法");
        }

        TimeMarkContent timeMarkContent = new TimeMarkContent()
                .setAuthorId(timeMarkContentVo.getAuthorId())
                .setAuthorName(timeMarkContentVo.getAuthorName())
                .setTimeBlockId(timeMarkContentVo.getTimeBlockId())
                .setTitle(timeMarkContentVo.getTitle());
        timeMarkContentMapper.insert(timeMarkContent);
        TimeMarkDetail timeMarkDetail = new TimeMarkDetail()
                .setMarkContentId(timeMarkContent.getId())
                .setContent(timeMarkContentVo.getContent());
        timeMarkDetailMapper.insert(timeMarkDetail);
        return new TimeMarkContentVO()
                .setId(timeMarkContent.getId())
                .setStatus(MarkContentStatusEnum.CREATED.getValue())
                .setSignCodeUrl("todo");
    }
}
