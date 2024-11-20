package com.example.citronix.web.rest;

import com.example.citronix.domain.service.FarmService;
import com.example.citronix.domain.service.dto.FarmDTO;
import com.example.citronix.web.errors.FieldMustBeNullException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


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

    @GetMapping("/{id}")
    public ResponseEntity<FarmDTO> getFarmById(@PathVariable Long id) {
        FarmDTO farm = farmService.findById(id);
        return ResponseEntity.ok(farm);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FarmDTO>> getAllFarms(Pageable pageable) {
        Page<FarmDTO> farms = farmService.findAll(pageable);
        return ResponseEntity.ok(farms);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FarmDTO>> searchFarms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<FarmDTO> farms = farmService.searchFarms(name, location, minArea, maxArea, startDate, endDate);
        return ResponseEntity.ok(farms);
    }
}