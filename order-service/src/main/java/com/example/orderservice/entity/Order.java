package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;
    @Column(name = "qty", nullable = false)
    private Integer qty;
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "create_at", nullable = false, updatable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDate createAt;


}
