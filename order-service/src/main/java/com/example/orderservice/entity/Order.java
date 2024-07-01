package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "purchase")
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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;


}
