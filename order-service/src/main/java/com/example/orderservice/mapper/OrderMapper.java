package com.example.orderservice.mapper;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order orderDTOToOrder(OrderDTO orderDTO);

    OrderDTO orderToOrderDTO(Order order);
    List<OrderDTO> orderToOrderDTO(List<Order> order);

    OrderDTO orderReqToOrderDTO(CreateOrderRequest request);

    OrderResponse orderDTOToOrderRes(OrderDTO orderDTO);
    List<OrderResponse> orderDTOToOrderRes(List<OrderDTO> orderDTO);



}
