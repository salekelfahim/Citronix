package com.example.citronix.domain.service.impl;

import com.example.citronix.domain.Farm;
import com.example.citronix.domain.Field;
import com.example.citronix.domain.service.FieldService;
import com.example.citronix.domain.service.dto.FieldDTO;
import com.example.citronix.domain.service.dto.mapper.FieldMapper;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.repository.FieldRepository;
import com.example.citronix.web.errors.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public Field update(Long id, Field updatedField) {
        Field existingField = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + id));

        Farm farm = existingField.getFarm();

        if (updatedField.getArea() < 0.1) throw new InvalidFieldAreaException();
        if (updatedField.getArea() > farm.getArea() * 0.5) throw new ExceededFieldAreaException();
        double totalFieldArea = fieldRepository.findByFarmId(farm.getId()).stream()
                .filter(f -> !f.getId().equals(id))
                .mapToDouble(Field::getArea)
                .sum();
        if (totalFieldArea + updatedField.getArea() >= farm.getArea()) throw new ExceededTotalFieldAreaException();

        existingField.setName(updatedField.getName());
        existingField.setArea(updatedField.getArea());
        return fieldRepository.save(existingField);
    }

    @Override
    public Field findById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + id));
    }

    @Override
    public List<Field> findByFarmId(Long farmId) {
        if (!farmRepository.existsById(farmId)) {
            throw new FarmNotFoundException();
        }
        return fieldRepository.findByFarmId(farmId);
    }

    @Override
    public void delete(Long id) {
        if (!fieldRepository.existsById(id)) {
            throw new EntityNotFoundException("Field not found with id: " + id);
        }
        fieldRepository.deleteById(id);
    }
}
