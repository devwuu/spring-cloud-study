package com.example.orderservice.controller;

import com.example.orderservice.common.ApiPrefix;
import com.example.orderservice.common.ApiResponse;
import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.exception.ApiException;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPrefix.ORDER_PREFIX)
@Slf4j
public class OrderController {

    private final Environment env;
    private final OrderService service;

    @GetMapping("/health_check")
    public String healthCheck(){
        return "Hello this is Order service on port " + env.getProperty("local.server.port");
    }

    @GetMapping("/{id}")
    public ApiResponse findByOrderId(@PathVariable("id") String orderId){
        OrderDTO order = service.findByOrderId(orderId);
        OrderResponse response = OrderMapper.INSTANCE.orderDTOToOrderRes(order);
        return ApiResponse.builder().status(200).data(response).build();
    }

    // todo path 고민...
    @PostMapping("/{userId}/orders")
    public ApiResponse save(
            @PathVariable("userId") String userId,
            @RequestBody @Validated CreateOrderRequest request,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            return ApiResponse.validationError(bindingResult);
        }
        OrderDTO orderDTO = OrderMapper.INSTANCE.orderReqToOrderDTO(request);
        orderDTO.setUserId(userId);
        OrderDTO save = service.save(orderDTO);
        OrderResponse response = OrderMapper.INSTANCE.orderDTOToOrderRes(save);
        return ApiResponse.builder().status(201).data(response).build();
    }

    // todo path 고민...
    @GetMapping("/{userId}/orders")
    public ApiResponse findByUserId(@PathVariable("userId") String userId){
        List<OrderDTO> orders = service.findByUserId(userId);
        List<OrderResponse> responses = OrderMapper.INSTANCE.orderDTOToOrderRes(orders);
        return ApiResponse.builder().status(200).data(responses).build();
    }




}
