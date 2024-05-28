package com.example.userservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .authorizeHttpRequests(requestMatcherRegistry ->
                        requestMatcherRegistry.requestMatchers("/users/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                )
                .headers(headersConfigurer -> 
                        headersConfigurer.frameOptions(frameOptionsConfig -> 
                                frameOptionsConfig.disable()));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
