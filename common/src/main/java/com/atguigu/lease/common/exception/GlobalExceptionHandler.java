package com.atguigu.lease.common.exception;

import com.atguigu.lease.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleException(Exception e) {

        e.printStackTrace();
        return Result.fail();
    }



    @ExceptionHandler(LeaseException.class)
    @ResponseBody
    public Result<String> handleNullPointerException(LeaseException e) {
        Integer code = e.getCode();
        String message = e.getMessage();
        return Result.fail(code,message );
    }

}
