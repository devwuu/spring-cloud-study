package com.example.userservice.common;

public enum ApiExceptionCode {

    ValidationException(400, "parameter validation error");

    Integer code;
    String message;

    ApiExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
