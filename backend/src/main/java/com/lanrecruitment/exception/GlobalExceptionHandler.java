package com.lanrecruitment.exception;

import com.lanrecruitment.common.Response;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Response<Void> handleBizException(BizException e) {
        return Response.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().isEmpty()
                ? "参数校验失败"
                : e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Response.fail(400, msg);
    }

    @ExceptionHandler(BindException.class)
    public Response<Void> handleBindException(BindException e) {
        String msg = e.getAllErrors().isEmpty()
                ? "参数校验失败"
                : e.getAllErrors().get(0).getDefaultMessage();
        return Response.fail(400, msg);
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception e) {
        return Response.fail(500, "系统异常");
    }
}
