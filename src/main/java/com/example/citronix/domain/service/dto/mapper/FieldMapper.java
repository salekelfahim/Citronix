package com.example.citronix.domain.service.dto.mapper;

import com.example.citronix.domain.Field;
import com.example.citronix.domain.service.dto.FieldDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    @Mapping(source = "farm.id", target = "farmId")
    FieldDTO toDTO(Field field);

    @Mapping(source = "farmId", target = "farm.id")
    Field toEntity(FieldDTO fieldDTO);
}
