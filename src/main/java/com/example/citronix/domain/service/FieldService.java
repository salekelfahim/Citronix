package com.example.citronix.domain.service;

import com.example.citronix.domain.Field;
import org.springframework.stereotype.Service;

@Service
public interface FieldService {
    Field save(Field field);
}
