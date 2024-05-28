package com.example.catalogservice.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;


@Data
public class CatalogDTO {

    private String productId;
    private String productName;
    private Integer stock;

    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

}
