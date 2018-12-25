package com.xyz.caofancpu.springdata.interceptor;

import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author caofanCPU
 */
@RestControllerAdvice
public class GlobalErrorInfoHandler {
    @ExceptionHandler(value = GlobalErrorInfoException.class)
    public ResultBody handleCustomerException(GlobalErrorInfoException exception) {
        return new ResultBody(exception.getCode(), exception.getMsg());
    }
}
