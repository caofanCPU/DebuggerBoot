package com.xyz.caofancpu.util.result;

import lombok.Getter;

public enum GlobalErrorInfoEnum implements ErrorInfoInterface {
    SUCCESS("200", "成功"),
    INTERNAL_ERROR("500", "服务器内部错误"),
    NOT_FOUND("404", "资源不存在"),
    PARA_ERROR("501", "请求参数错误"),
    GLOBAL_MS_MSG("998", "调用服务失败, 请重试"),
    GLOBAL_MSG("999", "请求处理失败, 请重试"),

    ;

    @Getter
    private String code;

    @Getter
    private String msg;

    GlobalErrorInfoEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.getMsg();
    }
}
