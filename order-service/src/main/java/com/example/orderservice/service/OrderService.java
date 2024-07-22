package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.ApiException;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public OrderDto save(OrderDto order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setTotalPrice(order.getQty() * order.getUnitPrice());
        Order entity = OrderMapper.INSTANCE.orderDtoToOrder(order);
        Order saved = repository.saveAndFlush(entity);
        return OrderMapper.INSTANCE.orderToOrderDto(saved);
    }

    public List<OrderDto> findByUserId(String userId) {
        List<Order> orders = repository.findByUserId(userId);
        return OrderMapper.INSTANCE.orderToOrderDto(orders);
    }

    public OrderDto findByOrderId(String orderId) {
        Optional<Order> optional = repository.findByOrderId(orderId);
        Order order = optional.orElseThrow(() -> new ApiException("Not exist order"));
        return OrderMapper.INSTANCE.orderToOrderDto(order);
    }


}
