package com.xyz.caofancpu.util.result;

public class GlobalErrorInfoException extends Exception implements ErrorInfoInterface {
    
    private String code;
    
    private String msg;
    
    public GlobalErrorInfoException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public GlobalErrorInfoException(ErrorInfoInterface infoInterface) {
        this.code = infoInterface.getCode();
        this.msg = infoInterface.getMsg();
    }
    
    public GlobalErrorInfoException(String msg) {
        this.code = GlobalErrorInfoEnum.GLOBAL_MSG.getCode();
        this.msg = msg;
    }
    
    public GlobalErrorInfoException() {
        this.code = GlobalErrorInfoEnum.GLOBAL_MSG.getCode();
        this.msg = GlobalErrorInfoEnum.GLOBAL_MSG.getMsg();
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public String getMsg() {
        return msg;
    }
}
