package com.example.catalogservice.entity;

import com.example.catalogservice.common.ApiExceptionCode;
import com.example.catalogservice.exception.ApiException;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "catalog")
@Data
public class Catalog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @Column(name = "created_at", updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDate createdAt;

    public Catalog reduce(Integer qty){
        if(this.stock < qty) {
            throw new ApiException(ApiExceptionCode.OutOfStock);
        }
        this.stock -= qty;
        return this;
    }



}
