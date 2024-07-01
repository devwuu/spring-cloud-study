package com.example.orderservice.common;

import lombok.Getter;

@Getter
public enum ApiExceptionCode {

    ValidationException(400, "parameter validation error"),
    NotFound(404, "not exist");

    Integer code;
    String message;

    ApiExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
