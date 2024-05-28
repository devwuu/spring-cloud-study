package com.example.catalogservice.mapper;

import com.example.catalogservice.dto.CatalogDTO;
import com.example.catalogservice.dto.CatalogResponse;
import com.example.catalogservice.entity.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CatalogMapper {

    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    CatalogDTO catalogToCatalogDTO(Catalog catalog);
    List<CatalogDTO> catalogToCatalogDTO(List<Catalog> catalog);

    CatalogResponse catalogDTOToCatalogRes(CatalogDTO dto);
    List<CatalogResponse> catalogDTOToCatalogRes(List<CatalogDTO> dto);

}
