package com.example.userservice.dto;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private LocalDateTime createdAt;

    private String encryptedPwd;

}
