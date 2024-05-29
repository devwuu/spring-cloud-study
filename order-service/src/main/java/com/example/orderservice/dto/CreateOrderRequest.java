package com.example.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull
    @Valid
    private String productId;

    @NotNull
    @Valid
    private Integer qty;

    @NotNull
    @Valid
    private Integer unitPrice;

}
