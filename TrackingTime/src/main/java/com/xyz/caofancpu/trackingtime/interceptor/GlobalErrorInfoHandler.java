package com.xyz.caofancpu.trackingtime.interceptor;

import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorInfoHandler {
    @ExceptionHandler(value = GlobalErrorInfoException.class)
    public ResultBody handleCustomerException(GlobalErrorInfoException exception) {
        return new ResultBody(exception.getCode(), exception.getMsg());
    }
}
