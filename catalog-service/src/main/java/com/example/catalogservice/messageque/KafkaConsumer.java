package com.example.catalogservice.messageque;

import com.example.catalogservice.common.ApiExceptionCode;
import com.example.catalogservice.entity.Catalog;
import com.example.catalogservice.exception.ApiException;
import com.example.catalogservice.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final CatalogRepository repository;

    @Transactional
    @KafkaListener(topics = "new_order_topic", groupId = "consumers")
    public void updateQty(String kafkaMessage){
        log.info("kafka message : {}", kafkaMessage);
        Map<Object, Object> map;
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new ApiException(ApiExceptionCode.DeserializeFail);
        }
        String productId = (String) map.get("productId");
        Optional<Catalog> optional = repository.findByProductId(productId);
        Catalog catalog = optional.orElseThrow(() -> new ApiException(ApiExceptionCode.NotFound));
        catalog.reduce((Integer) map.get("qty"));
    }

}
