package com.example.citronix.domain.service;

import com.example.citronix.domain.service.dto.FieldDTO;
import org.springframework.stereotype.Service;

@Service
public interface FieldService {
    FieldDTO save(FieldDTO fieldDTO);
}
