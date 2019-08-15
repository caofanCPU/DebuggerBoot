package com.xyz.caofancpu.trackingtime.constant.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 可以替换策略模式的枚举用法
 */
public enum MarkContentStatusEnum {
    CREATED(0, "已创建"),
    PUBLISHED(1, "已发布"),

    ON_BLOCK_CHAIN(9, "已上链"),
    DELETED(-1, "已删除"),

    ;

    @Getter
    private Integer value;
    @Getter
    private String title;

    MarkContentStatusEnum(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

    public static void main(String[] args) {
        List<MarkContentStatusEnum> flexibleDemoEnums = Arrays.asList(MarkContentStatusEnum.values());
        flexibleDemoEnums.sort(Comparator.comparing(MarkContentStatusEnum::getValue).reversed());
        flexibleDemoEnums.forEach(MarkContentStatusEnum::next);
    }

    public MarkContentStatusEnum find(Integer value) {
        return Arrays.stream(MarkContentStatusEnum.values()).findAny().orElse(MarkContentStatusEnum.DELETED);
    }

    public MarkContentStatusEnum next() {
        return this.find(this.getValue() + 1);
    }

    public MarkContentStatusEnum back() {
        switch (this) {
            case CREATED:
            case DELETED:
                return DELETED;
            case PUBLISHED:
                return CREATED;
            default:
                return this;
        }
    }

    public MarkContentStatusEnum clear() {
        return MarkContentStatusEnum.DELETED;
    }
}
