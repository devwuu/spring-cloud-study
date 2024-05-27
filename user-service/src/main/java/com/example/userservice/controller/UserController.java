package com.example.userservice.controller;

import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.exception.ApiException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.property.GreetingProperty;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final GreetingProperty prop;
    private final UserService service;

    @GetMapping("/health_check")
    public String status(){
        return "User Service Is Working";
    }

    @GetMapping("/welcome")
    public String greeting(){
        return prop.getMessage();
    }

    @PostMapping("")
    public String create(@RequestBody CreateUserRequest request,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("binding error : {}", bindingResult.toString());
            throw new ApiException("binding error");
        }
        UserDTO userDTO = UserMapper.INSTANCE.createUserReqToUserDTO(request);
        UserDTO saved = service.create(userDTO);
        return "Create New User";
    }


}
