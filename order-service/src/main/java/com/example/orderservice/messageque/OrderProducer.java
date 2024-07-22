package com.example.orderservice.messageque;

import com.example.orderservice.common.ApiExceptionCode;
import com.example.orderservice.dto.KafkaField;
import com.example.orderservice.dto.KafkaOrderDto;
import com.example.orderservice.dto.KafkaOrderDto.Payload;
import com.example.orderservice.dto.KafkaSchema;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> template;
    private final ObjectMapper mapper;
    private final List<KafkaField> FIELDS = List.of(
//        KafkaField.builder().type("int64").optional(false).field("id").build(),
        KafkaField.builder().type("string").optional(false).field("order_id").build(),
        KafkaField.builder().type("string").optional(false).field("product_id").build(),
        KafkaField.builder().type("int32").optional(false).field("qty").build(),
        KafkaField.builder().type("int32").optional(false).field("total_price").build(),
        KafkaField.builder().type("int32").optional(false).field("unit_price").build(),
        KafkaField.builder().type("string").optional(false).field("user_id").build()
    );
    private final KafkaSchema SCHEMA = KafkaSchema.builder()
            .type("struct")
            .optional(false)
            .name("purchase") // table 이름
            .fields(FIELDS).build();

    public OrderDto sendByKafkaPayload(String topic, OrderDto dto){

        Payload payload = Payload.builder()
                .order_id(dto.getOrderId())
                .user_id(dto.getUserId())
                .product_id(dto.getProductId())
                .qty(dto.getQty())
                .total_price(dto.getTotalPrice())
                .unit_price(dto.getUnitPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = KafkaOrderDto.builder().schema(SCHEMA).payload(payload).build();
        send(topic, kafkaOrderDto);
        return dto;
    }

    public OrderDto sendByDto(String topic, OrderDto dto){
        send(topic, dto);
        return dto;
    }

    private <T> boolean send(String topic, T target){
        String stringify = "";
        try {
            stringify = mapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            throw new ApiException(ApiExceptionCode.SerializeFail);
        }
        template.send(topic, stringify);
        log.info("Kafka producer send data..{}", target);
        return true;
    }


}
