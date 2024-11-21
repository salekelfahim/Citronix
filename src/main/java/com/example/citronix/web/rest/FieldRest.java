package com.example.citronix.web.rest;

import com.example.citronix.domain.Field;
import com.example.citronix.domain.service.FieldService;
import com.example.citronix.domain.service.dto.FieldDTO;
import com.example.citronix.domain.service.dto.mapper.FieldMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/fields")
public class FieldRest {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    public FieldRest(FieldService fieldService, FieldMapper fieldMapper) {
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<FieldDTO> save(@RequestBody FieldDTO fieldDTO) {
        Field field = fieldMapper.toEntity(fieldDTO);

        Field savedField = fieldService.save(field);

        return ResponseEntity.ok(fieldMapper.toDTO(savedField));
    }
}
