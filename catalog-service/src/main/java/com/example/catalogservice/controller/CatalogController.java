package com.example.catalogservice.controller;

import com.example.catalogservice.dto.CatalogDTO;
import com.example.catalogservice.dto.CatalogResponse;
import com.example.catalogservice.mapper.CatalogMapper;
import com.example.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;
    private final CatalogService service;

    @GetMapping("/health_check")
    public String find(){
        return String.format("Hello Catalog Service on %s", env.getProperty("local.server.port"));
    }

    @GetMapping("")
    public ResponseEntity findAll(){
        List<CatalogDTO> all = service.findAll();
        List<CatalogResponse> responses = CatalogMapper.INSTANCE.catalogDTOToCatalogRes(all);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

}
