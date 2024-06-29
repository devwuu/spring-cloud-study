package com.example.userservice.controller;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.common.ApiPrefix;
import com.example.userservice.common.ApiResponse;
import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.OrderResponse;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.property.GreetingProperty;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
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
    private final OrderServiceClient orderClient;

    @GetMapping("/health_check")
    public String status(){
        return String.format("User Service Is Working"
                + ", local port : " + env.getProperty("local.server.port")
                + ", server port : " + env.getProperty("server.port")
                + ", token secret : " + env.getProperty("token.secret")
                + ", token expiration time : " + env.getProperty("token.expiration-time")
                + ", refresh : " + env.getProperty("refresh")
        );
    }

    @GetMapping("/welcome")
    public String greeting(){
        return prop.getMessage();
    }

    @PostMapping("")
    public ApiResponse create(@RequestBody @Validated CreateUserRequest request,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ApiResponse.validationError(bindingResult);
        }
        UserDTO userDTO = UserMapper.INSTANCE.createUserReqToUserDTO(request);
        UserDTO saved = service.create(userDTO);
        UserResponse response = UserMapper.INSTANCE.userDTOToCreateUserRes(saved);
        return ApiResponse.builder().status(201).data(response).build();
    }

    @GetMapping("/{id}")
    public ApiResponse findByUserId(@PathVariable("id") String userId){
        UserDTO user = service.findByUserId(userId);
        UserResponse response = UserMapper.INSTANCE.userDTOToCreateUserRes(user);
        List<OrderResponse> orderResponses = orderClient.findByUserId(userId);
        response.setOrders(orderResponses);
        return ApiResponse.builder().status(200).data(response).build();
    }

    @GetMapping("")
    public ApiResponse findAll(){
        List<UserDTO> all = service.findAll();
        List<UserResponse> responses = UserMapper.INSTANCE.userDTOToCreateUserRes(all);
        return ApiResponse.builder().status(200).data(responses).build();

    }


}
