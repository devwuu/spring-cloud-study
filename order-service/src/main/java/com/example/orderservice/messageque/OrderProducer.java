package com.example.orderservice.messageque;

import com.example.orderservice.common.ApiExceptionCode;
import com.example.orderservice.dto.KafkaField;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> template;
    private final ObjectMapper mapper;
    private final List<KafkaField> FIELDS = List.of(
        new KafkaField("string", false, "order_id"),
        new KafkaField("string", false, "order_id"),
        new KafkaField("string", false, "order_id"),
        new KafkaField("string", false, "order_id"),
        new KafkaField("string", false, "order_id"),
        new KafkaField("string", false, "order_id"),
        new KafkaField("string", false, "order_id")
    );

    public OrderDto send(String topic, OrderDto dto){
        String stringify = "";
        try {
            stringify = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new ApiException(ApiExceptionCode.SerializeFail);
        }
        template.send(topic, stringify);
        log.info("Kafka producer send data..{}", stringify);
        return dto;
    }


}
