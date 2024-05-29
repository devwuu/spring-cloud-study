package com.example.orderservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDTO {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String userId;
    private String orderId;

    private LocalDate createAt;

}
