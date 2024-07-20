package com.example.orderservice.mapper;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order orderDtoToOrder(OrderDto orderDTO);

    OrderDto orderToOrderDto(Order order);
    List<OrderDto> orderToOrderDto(List<Order> order);

    OrderDto orderReqToOrderDto(CreateOrderRequest request);

    OrderResponse orderDtoToOrderRes(OrderDto orderDTO);
    List<OrderResponse> orderDtoToOrderRes(List<OrderDto> orderDTO);



}
