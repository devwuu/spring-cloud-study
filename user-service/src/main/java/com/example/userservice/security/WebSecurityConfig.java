package com.example.userservice.security;

import com.example.userservice.common.ApiPrefix;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

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
                                .requestMatchers(ApiPrefix.USER_PREFIX+"/health_check").permitAll()
                                .requestMatchers(ApiPrefix.USER_PREFIX+"/welcome").permitAll()
                                .requestMatchers(HttpMethod.POST, ApiPrefix.USER_PREFIX).permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated()
                        // user service 에서 인증이 이루어지지 않기 때문에 (gateway에서 authorization filter에서 이루어짐)
                        // authenticated가 걸린 request들은 403 에러 발생
                        // todo gateway 에서 넘어오는 request들은 모두 허용해주는 로직이 필요할 것으로 보임
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






}
