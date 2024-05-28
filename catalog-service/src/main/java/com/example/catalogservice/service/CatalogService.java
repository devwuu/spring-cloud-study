package com.example.catalogservice.service;

import com.example.catalogservice.dto.CatalogDTO;
import com.example.catalogservice.entity.Catalog;
import com.example.catalogservice.mapper.CatalogMapper;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository repository;

    public List<CatalogDTO> findAll(){
        List<Catalog> all = repository.findAll();
        return CatalogMapper.INSTANCE.catalogToCatalogDTO(all);
    }


}
