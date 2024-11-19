package com.example.citronix.domain.service.impl;

import com.example.citronix.domain.Farm;
import com.example.citronix.domain.service.FarmService;
import com.example.citronix.domain.service.dto.FarmDTO;
import com.example.citronix.domain.service.dto.mapper.FarmMapper;
import com.example.citronix.repository.FarmRepository;
import org.springframework.stereotype.Component;

@Component
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    public FarmServiceImpl(FarmRepository farmRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public FarmDTO save(FarmDTO farmDTO) {
        if (farmDTO.getFields() != null && !farmDTO.getFields().isEmpty()) {
            throw new FieldMustBeNullException("A farm cannot have fields at the time of creation.");
        }

        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDTO(farm);
    }

}
