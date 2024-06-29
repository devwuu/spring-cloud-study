package com.example.userservice.exception;

import com.example.userservice.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ApiResponse commonExceptionHandler(CommonException e) {
        return ApiResponse.builder()
                .status(e.getCode())
                .data(e.getType())
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
