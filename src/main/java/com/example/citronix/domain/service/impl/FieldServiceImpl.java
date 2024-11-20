package com.example.citronix.domain.service.impl;

import com.example.citronix.domain.Farm;
import com.example.citronix.domain.Field;
import com.example.citronix.domain.service.FieldService;
import com.example.citronix.domain.service.dto.FieldDTO;
import com.example.citronix.domain.service.dto.mapper.FieldMapper;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.repository.FieldRepository;
import com.example.citronix.web.errors.*;
import org.springframework.stereotype.Component;

@Component
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
        this.fieldMapper = fieldMapper;
    }
    @Override
    public FieldDTO save(FieldDTO fieldDTO) {
        Farm farm = farmRepository.findById(fieldDTO.getFarmId())
                .orElseThrow(FarmNotFoundException::new);

        if (fieldDTO.getArea() < 0.1) {
            throw new InvalidFieldAreaException();
        }

        if (fieldDTO.getArea() > farm.getArea() * 0.5) {
            throw new ExceededFieldAreaException();
        }

        double totalFieldArea = fieldRepository.findByFarmId(farm.getId()).stream()
                .mapToDouble(Field::getArea)
                .sum();
        if (totalFieldArea + fieldDTO.getArea() >= farm.getArea()) {
            throw new ExceededTotalFieldAreaException();
        }

        if (fieldRepository.countByFarmId(farm.getId()) >= 10) {
            throw new ExceededFieldCountException();
        }

        Field field = fieldMapper.toEntity(fieldDTO);
        field.setFarm(farm);
        field = fieldRepository.save(field);
        return fieldMapper.toDTO(field);
    }
}
