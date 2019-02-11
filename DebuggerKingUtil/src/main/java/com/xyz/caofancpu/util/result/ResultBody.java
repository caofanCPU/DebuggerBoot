package com.xyz.caofancpu.util.result;

import org.apache.commons.lang3.StringUtils;

public class ResultBody {
    
    private String code;
    private String msg;
    private Object data;
    
    public ResultBody(String code, String msg) {
        this.code = code;
        this.msg = msg;
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
    
    public Boolean isSuccess() {
        return GlobalErrorInfoEnum.SUCCESS.getCode().equals(this.code);
    }
    
    /**
     * 接口调用失败时, 用此方法向前端返回对应失败原因
     *
     * @param errorMsg
     * @return
     */
    public ResultBody fail(String errorMsg) {
        this.code = GlobalErrorInfoEnum.GLOBAL_MSG.getCode();
        this.msg = StringUtils.isBlank(errorMsg) ? GlobalErrorInfoEnum.GLOBAL_MSG.getMsg() : errorMsg;
        return this;
    }
    
    public ResultBody fail(String code, String errorMsg) {
        this.code = StringUtils.isBlank(code) ? GlobalErrorInfoEnum.GLOBAL_MSG.getCode() : code;
        this.msg = StringUtils.isBlank(errorMsg) ? GlobalErrorInfoEnum.GLOBAL_MSG.getMsg() : errorMsg;
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
