package com.example.orderservice.controller;

import com.example.orderservice.common.ApiPrefix;
import com.example.orderservice.common.ApiResponse;
import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.messageque.OrderProducer;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPrefix.ORDER_PREFIX)
@Slf4j
public class OrderController {

    private final Environment env;
    private final OrderService service;
    private final OrderProducer producer;

    @GetMapping("/health_check")
    public String healthCheck(){
        return "Hello this is Order service on port " + env.getProperty("local.server.port");
    }

    @GetMapping("/{id}")
    public ApiResponse findByOrderId(@PathVariable("id") String orderId){
        OrderDto order = service.findByOrderId(orderId);
        OrderResponse response = OrderMapper.INSTANCE.orderDtoToOrderRes(order);
        return ApiResponse.builder().status(200).data(response).build();
    }

    @PostMapping("/{userId}/orders")
    public ApiResponse save(
            @PathVariable("userId") String userId,
            @RequestBody @Validated CreateOrderRequest request,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            return ApiResponse.validationError(bindingResult);
        }

        OrderDto orderDTO = OrderMapper.INSTANCE.orderReqToOrderDto(request);
        orderDTO.setUserId(userId);
        orderDTO.setOrderId(UUID.randomUUID().toString());
        orderDTO.setTotalPrice(orderDTO.getQty() * orderDTO.getUnitPrice());

        // todo catalog service에서 구독하는 브로커와 sink 커넥터에서 구독하는 topic이 달라야할 필요가 있을까?
        // 물론 둘이 소비하는 dto는 다르긴 하지만... 암튼 고민해볼 필요가 있음

        // send order to broker (sink connector - purchase)
        producer.sendByKafkaPayload("order_created", orderDTO);

        // send order to broker (consumer - catalog service)
        // todo 이렇게 되면 재고 확인이 나중에 이루어지게 되는데.. transaction 관리가 필요하다 => SAGA pattern
        producer.sendByDto("new_order_topic", orderDTO); // consumer 에서 바라보고 있는 topic과 같은 topic으로

        OrderResponse response = OrderMapper.INSTANCE.orderDtoToOrderRes(orderDTO);
        return ApiResponse.builder().status(201).data(response).build();
    }

    // todo path 고민...
    @GetMapping("/{userId}/orders")
    public ApiResponse findByUserId(@PathVariable("userId") String userId){
        List<OrderDto> orders = service.findByUserId(userId);
        List<OrderResponse> responses = OrderMapper.INSTANCE.orderDtoToOrderRes(orders);
        return ApiResponse.builder().status(200).data(responses).build();
    }




}
