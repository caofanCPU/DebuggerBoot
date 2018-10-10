package com.xyz.caofancpu.result;

public enum GlobalErrorInfoEnum implements ErrorInfoInterface {
    SUCCESS("200", "成功"),
    INTERNAL_ERROR("500", "服务器内部错误"),
    NOT_FOUND("404", "资源不存在"),
    PARA_ERROR("501", "请求参数错误"),
    CALL_SERVICE_ERROR("801", "调用服务失败"),
    LOGIN_AUTH_FAILED("1003", "账号或密码错误"),
    
    ILLEGAL_BUS_ID("1001", "非法的业务ID"),
    
    ILLEGAL_PARAMETER("1002", "非法的参数"),
    
    ;
    
    
    private String code;
    
    private String msg;
    
    GlobalErrorInfoEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    @Override
    public String getCode() {
        return this.code;
    }
    
    @Override
    public String getMsg() {
        return this.msg;
    }
}
