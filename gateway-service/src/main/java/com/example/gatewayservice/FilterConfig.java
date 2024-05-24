package com.example.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    // application.yml 파일로 설정했던 gateway의 routes 설정을 config 파일로 처리한다
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(
                        pre -> pre.path("/users/**")
                                .filters(f -> f.addRequestHeader("gateway", "hello user")
                                        .addResponseHeader("gateway", "bye user")
                                )
                                .uri("http://localhost:8081")
                )
                .route(
                        pre -> pre.path("/catalogs/**")
                                .filters(f -> f.addRequestHeader("gateway", "hello catalog")
                                        .addResponseHeader("gateway", "bye catalog")
                                )
                                .uri("http://localhost:8082")
                )
                .build();
    }


}
