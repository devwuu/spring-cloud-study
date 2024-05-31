package com.example.gatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
public class LoggerFilter extends AbstractGatewayFilterFactory<LoggerFilter.Config> {

    @Data
    public static class Config{
        // yml 파일에 arg가 정의 되어 있다.
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    public LoggerFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain)->{
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("[Logging Filter] message: {}", config.getBaseMessage());
            if(config.isPreLogger()){
                log.info("[Logging PRE Filter] request id: {}", request.getId());
            }

            // post filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()) {
                    log.info("[Logging POST Filter] response status: {} ", response.getStatusCode());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE);
        // filter가 실행될 순서를 정해준다
        // Ordered.HIGHEST_PRECEDENCE
        // 제일 먼저 실행 제일 마지막에 종료
        // Ordered.LOWEST_PRECEDENCE
        // 제일 나중에 실행 제일 먼저 종료


        return filter;
    }


}
