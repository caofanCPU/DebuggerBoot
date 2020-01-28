package com.xyz.caofancpu.mvc.interceptor;

import com.xyz.caofancpu.util.result.D8API;
import com.xyz.caofancpu.util.result.D8Response;
import com.xyz.caofancpu.util.result.GlobalErrorInfoEnum;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorInfoHandler {
    @ExceptionHandler(value = GlobalErrorInfoException.class)
    public D8Response<Object> handleCustomerException(GlobalErrorInfoException ex) {
        return D8API.fail(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public D8Response<Object> handleRuntimeException(RuntimeException ex) {
        return D8API.fail(GlobalErrorInfoEnum.INTERNAL_ERROR);
    }
}
