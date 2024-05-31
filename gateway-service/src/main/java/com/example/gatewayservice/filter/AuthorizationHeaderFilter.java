package com.example.gatewayservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            // Auth 헤더 가져오기
            HttpHeaders headers = request.getHeaders();

            // 헤더가 없을 경우
            if(ObjectUtils.isEmpty(headers) || !headers.containsKey(HttpHeaders.AUTHORIZATION)){
                return apiExceptionHandler(exchange, HttpStatus.UNAUTHORIZED);
            }

            String bearer = headers.get(HttpHeaders.AUTHORIZATION).get(0);
            String token = bearer.replace("Bearer ", "");

            if(!isValidToken(token)){
                return apiExceptionHandler(exchange, HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    // controller가 있는 mvc 패턴이 아니기 때문에
    // webflux를 이용하여 응답해주기로 한다 (비동기 방식)
    private Mono<Void> apiExceptionHandler(ServerWebExchange exchange, HttpStatus httpStatus) {
        byte[] message = "Invalid Token".getBytes(StandardCharsets.UTF_8);
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = response.bufferFactory().wrap(message);
        response.setStatusCode(httpStatus);
        return response.writeWith(Flux.just(buffer));
    }


    private boolean isValidToken(String token) {
        byte[] decode = Decoders.BASE64.decode(env.getProperty("token.secret"));
        SecretKey key = Keys.hmacShaKeyFor(decode);
        try {
            Claims payload = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseClaimsJws(token)
                    .getPayload();
            String sub = (String) payload.get("sub");
            log.info("sub:: {}", sub);
            return StringUtils.hasText(sub);
        }catch (Exception e){
            return false;
        }
    }


    public static class Config{

    }


}
