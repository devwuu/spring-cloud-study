package com.example.userservice.controller;

import com.example.userservice.common.ApiPrefix;
import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.exception.ApiException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.property.GreetingProperty;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPrefix.USER_PREFIX)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final GreetingProperty prop;
    private final UserService service;
//    private final ServletWebServerApplicationContext context;
    private final Environment env;

    @GetMapping("/health_check")
    public String status(){
        return String.format("User Service Is Working"
                + ", local port : " + env.getProperty("local.server.port")
                + ", server port : " + env.getProperty("server.port")
                + ", token secret : " + env.getProperty("token.secret")
                + ", token expiration time : " + env.getProperty("token.expiration-time")
        );



    }

    @GetMapping("/welcome")
    public String greeting(){
        return prop.getMessage();
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody @Validated CreateUserRequest request,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("binding error : {}", bindingResult.toString()); // todo binding exception return 해주는 로직
            throw new ApiException("binding error");
        }
        UserDTO userDTO = UserMapper.INSTANCE.createUserReqToUserDTO(request);
        UserDTO saved = service.create(userDTO);
        UserResponse response = UserMapper.INSTANCE.userDTOToCreateUserRes(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity findByUserId(@PathVariable("id") String userId){
        UserDTO user = service.findByUserId(userId);
        UserResponse response = UserMapper.INSTANCE.userDTOToCreateUserRes(user);
        response.setOrders(null); // todo order API 추가
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity findAll(){
        List<UserDTO> all = service.findAll();
        List<UserResponse> responses = UserMapper.INSTANCE.userDTOToCreateUserRes(all);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }


}
