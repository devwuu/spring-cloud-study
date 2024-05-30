package com.example.userservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "email cannot be null")
    @Size(min = 2)
    @Email
    @Valid
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min = 8)
    @Valid
    private String pwd;


}
