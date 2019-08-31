package com.xyz.caofancpu.util.result;

public interface ErrorInfoInterface {

    String getCode();

    String getMsg();

    /**
     * 兼容底层框架内部记录异常信息
     *
     * @return
     */
    String getMessage();
}
