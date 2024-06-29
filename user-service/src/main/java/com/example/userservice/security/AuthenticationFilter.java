package com.example.userservice.security;

import com.example.userservice.common.ApiExceptionCode;
import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.exception.CommonException;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService service;
    private final Environment env; //todo property로 변환 검토

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        log.info("trying to authenticate");

        try {
            LoginRequest credential = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            // 사용자 정보를 인증 받기 위한 token으로 만들기
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credential.getEmail(), credential.getPwd(), new ArrayList<>());
            Authentication authenticate = getAuthenticationManager().authenticate(authenticationToken); // 인증 처리 요청
            return authenticate;

        } catch (IOException e) {
            throw new CommonException(ApiExceptionCode.ValidationException, e); // parameter 맵핑 실패
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User principal = (User) authResult.getPrincipal();
        UserDTO user = service.findByEmail(principal.getUsername());
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .subject(user.getUserId())
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration-time"))))
                .compact();

        response.addHeader("token", token);
        response.addHeader("user_id", user.getUserId());

        log.info("successful login!! {}", user);

    }


}
