package com.xyz.caofancpu.result;

public enum GlobalErrorInfoEnum implements ErrorInfoInterface {
    SUCCESS("200", "success"),
    INTERNAL_ERROR("500", "server internal error"),
    NOT_FOUND("404", "service not found"),
    PARA_ERROR("501", "parameters have error"),
    CALL_SERVICE_ERROR("801", "call service error"),
    
    ILLEGAL_BUS_ID("1001", "illegal bus id"),
    
    ILLEGAL_PARAMETER("1002", "illegal parameter"),;
    
    
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
