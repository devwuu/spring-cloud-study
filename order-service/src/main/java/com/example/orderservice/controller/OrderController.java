package com.example.orderservice.controller;

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
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final Environment env;
    private final OrderService service;

    @GetMapping("/health_check")
    public String healthCheck(){
        return "Hello this is Order service on port " + env.getProperty("local.server.port");
    }

    @GetMapping("/{id}")
    public ResponseEntity findByOrderId(@PathVariable("id") String orderId){
        OrderDTO order = service.findByOrderId(orderId);
        OrderResponse response = OrderMapper.INSTANCE.orderDTOToOrderRes(order);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // todo path 고민...
    @PostMapping("/{userId}/orders")
    public ResponseEntity save(
            @PathVariable("userId") String userId,
            @RequestBody @Validated CreateOrderRequest request,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            // todo binding result 를 exception message 로 전달
            log.info("bindingResult has errors...{}", bindingResult.getAllErrors());
            throw new ApiException("Parameter error");
        }
        OrderDTO orderDTO = OrderMapper.INSTANCE.orderReqToOrderDTO(request);
        orderDTO.setUserId(userId);
        OrderDTO save = service.save(orderDTO);
        OrderResponse response = OrderMapper.INSTANCE.orderDTOToOrderRes(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // todo path 고민...
    @GetMapping("/{userId}/orders")
    public ResponseEntity findByUserId(@PathVariable("userId") String userId){
//        List<OrderDTO> orders = service.findByUserId(userId);  todo order 예제 데이터 추가 후 수정
        OrderDTO testOrder = OrderDTO.builder()
                .orderId("1")
                .userId(userId)
                .qty(10)
                .productId("1")
                .totalPrice(200000)
                .unitPrice(2000)
                .build();
        List<OrderDTO> orders = List.of(testOrder);
        List<OrderResponse> responses = OrderMapper.INSTANCE.orderDTOToOrderRes(orders);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }




}
