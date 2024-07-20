package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaOrderDto {

    private KafkaSchema schema;
    private Payload payload;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Payload{
        private Long id;
        private String product_id;
        private Integer qty;
        private Integer unit_price;
        private Integer total_price;
        private String user_id;
        private String order_id;
        private LocalDate created_at;
    }

}
