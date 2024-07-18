package com.example.orderservice.messageque;

import com.example.orderservice.common.ApiExceptionCode;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.exception.ApiException;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> template;

    public OrderDTO send(String topic, OrderDTO dto){
        ObjectMapper mapper = new ObjectMapper();
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
