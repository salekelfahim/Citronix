package com.example.citronix.web.rest;

import com.example.citronix.domain.Field;
import com.example.citronix.service.FieldService;
import com.example.citronix.dto.FieldDTO;
import com.example.citronix.dto.mapper.FieldMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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

    @PutMapping("/{id}")
    public ResponseEntity<FieldDTO> update(@PathVariable Long id, @RequestBody FieldDTO fieldDTO) {
        Field field = fieldMapper.toEntity(fieldDTO);
        Field updatedField = fieldService.update(id, field);
        return ResponseEntity.ok(fieldMapper.toDTO(updatedField));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getById(@PathVariable Long id) {
        Field field = fieldService.findById(id);
        return ResponseEntity.ok(fieldMapper.toDTO(field));
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<FieldDTO>> getByFarmId(@PathVariable Long farmId) {
        List<Field> fields = fieldService.findByFarmId(farmId);
        List<FieldDTO> fieldDTOs = fields.stream().map(fieldMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(fieldDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fieldService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
