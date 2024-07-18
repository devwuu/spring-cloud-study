package com.example.catalogservice.common;

import lombok.Getter;

@Getter
public enum ApiExceptionCode {

    ValidationException(400, "parameter validation error"),
    NotFound(404, "not exist"),
    DeserializeFail(400, "data cannot be deserialize"),

    OutOfStock(400, "sold out");

    Integer code;
    String message;

    ApiExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
