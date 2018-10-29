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
    
    /**
     * 接口调用失败时, 用此方法向前端返回对应失败原因
     *
     * @param errorMsg
     * @return
     */
    public ResultBody fail(String errorMsg) {
        this.code = GlobalErrorInfoEnum.ILLEGAL_BUS_ID.getCode();
        this.msg = StringUtils.isEmpty(errorMsg) ? GlobalErrorInfoEnum.ILLEGAL_BUS_ID.getCode() : errorMsg;
        return this;
    }
    
    /**
     * 接口调用成功, 返回指定数据
     *
     * @param data
     * @return
     */
    public ResultBody success(Object data) {
        this.data = data;
        return this;
    }
    
    /**
     * 接口调用成功, 返回指定数据及提示消息
     *
     * @param data
     * @param successMsg
     * @return
     */
    public ResultBody success(Object data, String successMsg) {
        this.data = data;
        this.msg = successMsg;
        return this;
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
    
    @Override
    public String toString() {
        return "ResultBody{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
