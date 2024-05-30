package com.example.userservice.security;

import com.example.userservice.common.ApiPrefix;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .authorizeHttpRequests(requestMatcherRegistry ->
                        requestMatcherRegistry.requestMatchers(ApiPrefix.USER_PREFIX+"/login").permitAll()
                                .requestMatchers(ApiPrefix.USER_PREFIX+"/health_check").permitAll()
                                .requestMatchers(ApiPrefix.USER_PREFIX+"/welcome").permitAll()
                                .requestMatchers(HttpMethod.POST, ApiPrefix.USER_PREFIX).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilter(getAuthenticationFilter(authenticationManager))
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

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }






}
