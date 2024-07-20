package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaField {

    private String type;
    private boolean optional;
    private String name; // todo 필수 여부 확인 필요
    private Integer version; // todo 필수 여부 확인 필요
    private String field;

}
