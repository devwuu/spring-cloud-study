package com.example.userservice.controller;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.common.ApiPrefix;
import com.example.userservice.common.ApiResponse;
import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.OrderResponse;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserDto;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.property.GreetingProperty;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
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
    private final Environment env;
    private final OrderServiceClient orderClient;

    private final CircuitBreakerFactory circuitBreakerFactory;

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
        UserDto userDto = UserMapper.INSTANCE.createUserReqToUserDto(request);
        UserDto saved = service.create(userDto);
        UserResponse response = UserMapper.INSTANCE.userDtoToCreateUserRes(saved);
        return ApiResponse.builder().status(201).data(response).build();
    }

    @GetMapping("/{id}")
    public ApiResponse findByUserId(@PathVariable("id") String userId){
        UserDto user = service.findByUserId(userId);
        UserResponse response = UserMapper.INSTANCE.userDtoToCreateUserRes(user);

//        ApiResponse<List<OrderResponse>> orderResponses = orderClient.findByUserId(userId);
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("order-client-circuit-breaker");
        ApiResponse<List<OrderResponse>> orderResponses = circuitBreaker.run(() -> orderClient.findByUserId(userId),
                (throwable) -> ApiResponse.<List<OrderResponse>>builder().data(List.of()).build());

        response.setOrders(orderResponses.getData());
        return ApiResponse.builder().status(200).data(response).build();
    }

    @GetMapping("")
    public ApiResponse findAll(){
        List<UserDto> all = service.findAll();
        List<UserResponse> responses = UserMapper.INSTANCE.userDtoToCreateUserRes(all);
        return ApiResponse.builder().status(200).data(responses).build();
    }


}
