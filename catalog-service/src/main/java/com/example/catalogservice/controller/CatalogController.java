package com.example.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;

    @GetMapping("/health_check")
    public String find(){
        return String.format("Hello Catalog Service on %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/message")
    public String msg(@RequestHeader("gateway") String gateway){
        return "gateway: "+gateway;
    }

}
