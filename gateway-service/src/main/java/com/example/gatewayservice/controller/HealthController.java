package com.example.gatewayservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class HealthController {

    private final Environment env;

    @GetMapping("/health_check")
    public String status(){
        return String.format("User Service Is Working"
                + ", local port : " + env.getProperty("local.server.port")
                + ", server port : " + env.getProperty("server.port")
                + ", token secret : " + env.getProperty("token.secret")
                + ", token expiration time : " + env.getProperty("token.expiration-time")
                + ", refresh : " + env.getProperty("refresh")
        );

    }


}
