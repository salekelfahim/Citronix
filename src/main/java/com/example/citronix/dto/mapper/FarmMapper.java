package com.example.citronix.dto.mapper;

import com.example.citronix.domain.Farm;
import com.example.citronix.dto.FarmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(target = "fields", ignore = true)
    Farm toEntity(FarmDTO farmDTO);

    @Mapping(target = "fields", ignore = true)
    FarmDTO toDTO(Farm farm);
}