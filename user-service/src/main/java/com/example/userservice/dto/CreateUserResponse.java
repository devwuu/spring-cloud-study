package com.example.userservice.dto;


import lombok.Data;

@Data
public class CreateUserResponse {

    private Long id;
    private String userId;
    private String email;

}
