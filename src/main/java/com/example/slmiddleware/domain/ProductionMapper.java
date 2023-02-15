package com.example.slmiddleware.domain;

import com.example.slmiddleware.dto.EmptyProductionDto;
import com.example.slmiddleware.service.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductionMapper extends EntityMapper<Production_Day_TB, EmptyProductionDto> {

    ProductionMapper MAPPER = Mappers.getMapper(ProductionMapper.class);

    Production_Day_TB toEntity(final EmptyProductionDto dto);
}
