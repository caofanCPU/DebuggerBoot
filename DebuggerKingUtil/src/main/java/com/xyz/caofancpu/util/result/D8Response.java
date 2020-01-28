package com.xyz.caofancpu.util.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class D8Response<T> {

    @ApiModelProperty(value = "状态码", example = "200", position = 1)
    private String code;
    @ApiModelProperty(value = "报错信息", example = "请求参数错误", position = 2)
    private String msg;
    @ApiModelProperty(value = "结果数据", position = 3)
    private T data;

    public D8Response() {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean ifSuccess() {
        return GlobalErrorInfoEnum.SUCCESS.getCode().equals(this.code);
    }

}
