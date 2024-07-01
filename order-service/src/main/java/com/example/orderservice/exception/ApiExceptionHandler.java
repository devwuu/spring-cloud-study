package com.example.orderservice.exception;

import com.example.orderservice.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ApiResponse apiExceptionHandler(ApiException e) {
        return ApiResponse.builder()
                .status(e.getCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse exceptionHandler(Exception e) {

        log.error(e.getMessage(), e);

        return ApiResponse.builder()
                .status(500)
                .message("Internal Server Error")
                .build();
    }

}
