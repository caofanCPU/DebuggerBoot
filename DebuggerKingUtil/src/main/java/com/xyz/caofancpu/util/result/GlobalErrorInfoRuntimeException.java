package com.xyz.caofancpu.util.result;

public class GlobalErrorInfoRuntimeException extends RuntimeException implements ErrorInfoInterface {
    
    private String code;
    
    private String msg;
    
    public GlobalErrorInfoRuntimeException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public GlobalErrorInfoRuntimeException(String msg) {
        this.code = GlobalErrorInfoEnum.GLOBAL_MSG.getCode();
        this.msg = msg;
    }
    
    public GlobalErrorInfoRuntimeException() {
        this.code = GlobalErrorInfoEnum.GLOBAL_MSG.getCode();
        this.msg = GlobalErrorInfoEnum.GLOBAL_MSG.getMsg();
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
