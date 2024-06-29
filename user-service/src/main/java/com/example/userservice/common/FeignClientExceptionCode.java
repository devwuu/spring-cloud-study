package com.example.userservice.common;

import lombok.Getter;

@Getter
public enum FeignClientExceptionCode {

    NotFound(404, "not exist"),
    DefaultException(500, "Fegin client exception"),;

    Integer code;
    String message;

    FeignClientExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
