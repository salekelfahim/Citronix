package com.example.citronix.web.rest;

import com.example.citronix.domain.service.FarmService;
import com.example.citronix.domain.service.dto.FarmDTO;
import com.example.citronix.web.errors.FieldMustBeNullException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/farms")
public class FarmRest {

    private final FarmService farmService;

    public FarmRest(FarmService farmService) {
        this.farmService = farmService;
    }

    @PostMapping("/save")
    public ResponseEntity<FarmDTO> createFarm(@RequestBody FarmDTO farmDTO) {
        try {
            FarmDTO result = farmService.save(farmDTO);
            return ResponseEntity.ok(result);
        } catch (FieldMustBeNullException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmDTO> updateFarm(@PathVariable Long id, @RequestBody FarmDTO farmDTO) {
        FarmDTO updatedFarm = farmService.update(id, farmDTO);
        return ResponseEntity.ok(updatedFarm);
    }
}