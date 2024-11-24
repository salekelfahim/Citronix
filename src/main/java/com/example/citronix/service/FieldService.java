package com.example.citronix.service;

import com.example.citronix.domain.Field;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldService {
    Field save(Field field);
    Field update(Long id, Field field);
    Field findById(Long id);
    List<Field> findByFarmId(Long farmId);
    void delete(Long id);
}
