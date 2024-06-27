package com.example.userservice.client;

import com.example.userservice.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/orders/{userId}/orders")
    List<OrderResponse> findByUserId(@PathVariable String userId);

}
