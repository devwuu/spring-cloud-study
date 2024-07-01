package com.example.orderservice.exception;

import com.example.orderservice.common.ApiExceptionCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private Integer code;
    private String message;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(ApiExceptionCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ApiException(ApiExceptionCode code, Throwable cause) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
