package com.xyz.caofancpu.util.result;

import lombok.Getter;

public class CustomerErrorInfo implements ErrorInfoInterface {

    @Getter
    private String code;

    @Getter
    private String msg;

    public CustomerErrorInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CustomerErrorInfo(String msg) {
        this.code = GlobalErrorInfoEnum.GLOBAL_MSG.getCode();
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.getMsg();
    }
}
