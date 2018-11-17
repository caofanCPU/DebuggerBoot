package com.xyz.caofancpu.util.result;

public class GlobalErrorInfoException extends Exception {
    private ErrorInfoInterface errorInfo;
    
    public GlobalErrorInfoException(ErrorInfoInterface errorInfo) {
        this.errorInfo = errorInfo;
    }
    
    public ErrorInfoInterface getErrorInfo() {
        return errorInfo;
    }
    
    public void setErrorInfo(ErrorInfoInterface errorInfo) {
        this.errorInfo = errorInfo;
    }
    
    /**
     * 自定义异常信息, 复写
     *
     * @return
     */
    @Override
    public String getMessage() {
        return errorInfo.getMsg();
    }
}
