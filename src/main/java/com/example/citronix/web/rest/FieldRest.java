package com.example.citronix.web.rest;

import com.example.citronix.domain.service.FieldService;
import com.example.citronix.domain.service.dto.FieldDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/fields")
public class FieldRest {

    private final FieldService fieldService;

    public FieldRest(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping("/save")
    public ResponseEntity<FieldDTO> save(@RequestBody FieldDTO fieldDTO) {
        return ResponseEntity.ok(fieldService.save(fieldDTO));
    }
}
