package com.xyz.caofancpu.result;

public class CustomerErrorInfo implements ErrorInfoInterface {
    
    private String code;
    
    private String msg;
    
    public CustomerErrorInfo(String code, String msg) {
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
