package com.xyz.caofancpu.util.result;

import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public abstract class D8API<T> extends D8Response<T> {

    public D8API() {
    }

    /**
     * 接口调用失败时, 用此方法向前端返回对应失败原因
     *
     * @param errorMsg
     * @return
     */
    public static <T> D8Response<T> fail(String errorMsg) {
        D8Response<T> d8Response = new D8Response<>();
        d8Response.setCode(GlobalErrorInfoEnum.GLOBAL_MSG.getCode());
        d8Response.setMsg(StringUtils.isBlank(errorMsg) ? GlobalErrorInfoEnum.GLOBAL_MSG.getMsg() : errorMsg);
        return d8Response;
    }

    public static <T> D8Response<T> fail(ErrorInfoInterface errorInfo) {
        D8Response<T> d8Response = new D8Response<>();
        d8Response.setCode(errorInfo.getCode());
        d8Response.setMsg(errorInfo.getMsg());
        return d8Response;
    }


    public static <T> D8Response<T> fail(String code, String errorMsg) {
        D8Response<T> d8Response = new D8Response<>();
        d8Response.setCode(StringUtils.isBlank(code) ? GlobalErrorInfoEnum.GLOBAL_MSG.getCode() : code);
        d8Response.setMsg(StringUtils.isBlank(errorMsg) ? GlobalErrorInfoEnum.GLOBAL_MSG.getMsg() : errorMsg);
        return d8Response;
    }

    /**
     * 接口调用成功, 返回指定数据
     *
     * @param data
     * @return
     */
    public static <T> D8Response<T> success(T data) {
        D8Response<T> d8Response = new D8Response<>();
        d8Response.setCode(GlobalErrorInfoEnum.SUCCESS.getCode());
        d8Response.setMsg(GlobalErrorInfoEnum.SUCCESS.getMsg());
        d8Response.setData(data);
        return d8Response;
    }

    /**
     * 接口调用成功, 返回指定数据及提示消息
     *
     * @param data
     * @param successMsg
     * @return
     */
    public static <T> D8Response<T> success(T data, String successMsg) {
        D8Response<T> d8Response = new D8Response<>();
        d8Response.setCode(GlobalErrorInfoEnum.SUCCESS.getCode());
        d8Response.setData(data);
        d8Response.setMsg(successMsg);
        return d8Response;
    }
}
