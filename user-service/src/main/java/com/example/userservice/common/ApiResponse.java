package com.example.userservice.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static com.example.userservice.common.ApiExceptionCode.ValidationException;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@Builder
public class ApiResponse<T> {

    private T data;
    private Integer status;
    private String message;

    public static ApiResponse validationError(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            builder.append(field).append(" : ")
                    .append(message).append("\n");
        }
        return ApiResponse.builder()
                .status(ValidationException.code)
                .message(ValidationException.message)
                .data(bindingResult.toString())
                .build();
    }
}
