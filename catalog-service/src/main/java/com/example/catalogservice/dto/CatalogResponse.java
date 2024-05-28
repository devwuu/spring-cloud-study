package com.example.catalogservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatalogResponse {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
}
