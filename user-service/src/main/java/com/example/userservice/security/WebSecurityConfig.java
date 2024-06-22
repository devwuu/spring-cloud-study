package com.example.userservice.security;

import com.example.userservice.common.ApiPrefix;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final Environment env;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            UserService service,
            Environment environment
            ) throws Exception {

        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .authorizeHttpRequests(requestMatcherRegistry ->
                        requestMatcherRegistry
//                                .requestMatchers(ApiPrefix.USER_PREFIX+"/health_check").permitAll()
//                                .requestMatchers(ApiPrefix.USER_PREFIX+"/welcome").permitAll()
//                                .requestMatchers(HttpMethod.POST, ApiPrefix.USER_PREFIX).permitAll()
//                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/**").access((authentication, object) -> isGatewayIp(authentication, object)) // gateway에서 보낸 request들은 이미 인증 된 request이기 때문에 모두 허용함
                                .anyRequest().authenticated() // user service로 바로 요청하는 모든 request 방어
                )
                .addFilter(getAuthenticationFilter(environment, service, authenticationManager))
                .headers(headersConfigurer ->
                        headersConfigurer.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserService service, BCryptPasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(service);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    private AuthenticationFilter getAuthenticationFilter(
            Environment environment,
            UserService service,
            AuthenticationManager authenticationManager) {

        AuthenticationFilter filter = new AuthenticationFilter(service, environment);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    private AuthorizationDecision isGatewayIp(Supplier<Authentication> authentication, RequestAuthorizationContext object){
        HttpServletRequest request = object.getRequest();
        String remoteAddr = request.getRemoteAddr();
        return new AuthorizationDecision(env.getProperty("gateway.ip").matches(remoteAddr));
    }






}
