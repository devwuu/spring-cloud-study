package com.example.userservice.exception;

import com.example.userservice.common.ApiExceptionCode;
import com.example.userservice.common.FeignClientExceptionCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private String type;
    private Integer code;
    private String message;

    public CommonException(ApiExceptionCode code, Throwable cause) {
        super(cause);
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public CommonException(ApiExceptionCode code) {
        this.type = "API Exception";
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public CommonException(FeignClientExceptionCode code) {
        this.type = "Feign Client Exception";
        this.code = code.getCode();
        this.message = code.getMessage();
    }

}
