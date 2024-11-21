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

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public Field save(Field field) {
        Farm farm = farmRepository.findById(field.getFarm().getId())
                .orElseThrow(FarmNotFoundException::new);

        if (field.getArea() < 0.1) {
            throw new InvalidFieldAreaException();
        }

        if (field.getArea() > farm.getArea() * 0.5) {
            throw new ExceededFieldAreaException();
        }

        double totalFieldArea = fieldRepository.findByFarmId(farm.getId()).stream()
                .mapToDouble(Field::getArea)
                .sum();
        if (totalFieldArea + field.getArea() >= farm.getArea()) {
            throw new ExceededTotalFieldAreaException();
        }

        if (fieldRepository.countByFarmId(farm.getId()) >= 10) {
            throw new ExceededFieldCountException();
        }

        field.setFarm(farm);
        return fieldRepository.save(field);
    }
}
