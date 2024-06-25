package com.example.userservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced // order-serivce를 유레카에 등록되어 있는 도메인으로 대체할 수 있게 한다
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
