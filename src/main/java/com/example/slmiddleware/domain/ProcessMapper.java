package com.example.slmiddleware.domain;

import com.example.slmiddleware.dto.ResponseProcessMsgDto;
import com.example.slmiddleware.service.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProcessMapper extends EntityMapper<Process_TB, ResponseProcessMsgDto> {

    ProcessMapper MAPPER = Mappers.getMapper(ProcessMapper.class);

    Process_TB toEntity(final ResponseProcessMsgDto dto);
}
