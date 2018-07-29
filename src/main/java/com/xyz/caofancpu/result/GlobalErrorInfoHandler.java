package com.xyz.caofancpu.result;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalErrorInfoHandler {
    @ExceptionHandler(value = GlobalErrorInfoException.class)
    public ResultBody errorHandlerOverJson(
            HttpServletRequest request,
            GlobalErrorInfoException exception) {
        ErrorInfoInterface errorInfo = exception.getErrorInfo();
        ResultBody result = new ResultBody(errorInfo);
        return result;
    }
    
}
