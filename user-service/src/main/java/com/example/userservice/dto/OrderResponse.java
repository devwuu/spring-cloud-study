package com.example.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderResponse {

    private String orderId;

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createdAt;


}
