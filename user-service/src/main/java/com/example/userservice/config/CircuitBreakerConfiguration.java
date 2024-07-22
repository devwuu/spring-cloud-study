package com.example.userservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer(){

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(4) // circuit breaker 를 open 하는 실패 비율
                .waitDurationInOpenState(Duration.ofMillis(1000)) // circuit breaker 의 open 상태를 유지하는 지속시간, 이 시간 이 후 half open 상태
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // circuit breaker 가 close 될 때 호출 결과를 기록하는데 사용되는 슬라이딩 창의 유형, 기본이 count base
                .slidingWindowSize(2) // 호출 결과를 기록하는 데 사용되는 슬라이딩 창의 크기를 구성, count 기반이기 때문에 2번
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4)) // supplier 의 time out 을 정해준다. 이 시간이 지나면 circuit breaker open 됨. 여기서 supplier 는 다른 마이크로 애플리케이션과의 API 통신을 의미한다.
                .build();


        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }


}
