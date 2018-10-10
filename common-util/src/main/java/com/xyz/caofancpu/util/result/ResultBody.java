package com.xyz.caofancpu.util.result;

import org.apache.commons.lang3.StringUtils;

public class ResultBody {
    
    private String code;
    private String msg;
    private Object data;
    
    
    public ResultBody(ErrorInfoInterface errorInfo) {
        this.code = errorInfo.getCode();
        this.msg = errorInfo.getMsg();
    }
    
    public ResultBody(Object data) {
        this.code = GlobalErrorInfoEnum.SUCCESS.getCode();
        this.msg = GlobalErrorInfoEnum.SUCCESS.getMsg();
        this.data = data;
    }
    
    public ResultBody() {
        this.code = GlobalErrorInfoEnum.SUCCESS.getCode();
        this.msg = GlobalErrorInfoEnum.SUCCESS.getMsg();
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public void fail(String msg) {
        this.msg = StringUtils.isEmpty(code) ? GlobalErrorInfoEnum.INTERNAL_ERROR.getMsg() : msg;
    }
    
    public void fail(String code, String msg) {
        this.code = StringUtils.isEmpty(code) ? GlobalErrorInfoEnum.INTERNAL_ERROR.getCode() : code;
        this.msg = StringUtils.isEmpty(code) ? GlobalErrorInfoEnum.INTERNAL_ERROR.getMsg() : msg;
    }
    
    @Override
    public String toString() {
        return "ResultBody{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
