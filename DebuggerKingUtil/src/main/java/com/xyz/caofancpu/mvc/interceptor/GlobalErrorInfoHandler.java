package com.xyz.caofancpu.mvc.interceptor;

import com.xyz.caofancpu.util.result.GlobalErrorInfoEnum;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorInfoHandler {
    @ExceptionHandler(value = GlobalErrorInfoException.class)
    public ResultBody handleCustomerException(GlobalErrorInfoException ex) {
        return new ResultBody(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResultBody handleRuntimeException(RuntimeException ex) {
        return new ResultBody(GlobalErrorInfoEnum.INTERNAL_ERROR);
    }
}
