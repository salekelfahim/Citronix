package com.example.citronix.web.rest;

import com.example.citronix.domain.Farm;
import com.example.citronix.domain.service.FarmService;
import com.example.citronix.domain.service.dto.FarmDTO;
import com.example.citronix.domain.service.dto.mapper.FarmMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/farms")
public class FarmRest {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    public FarmRest(FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<FarmDTO> createFarm(@RequestBody FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        Farm savedFarm = farmService.save(farm);
        return ResponseEntity.ok(farmMapper.toDTO(savedFarm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmDTO> updateFarm(@PathVariable Long id, @RequestBody FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO);
        Farm updatedFarm = farmService.update(id, farm);
        return ResponseEntity.ok(farmMapper.toDTO(updatedFarm));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmDTO> getFarmById(@PathVariable Long id) {
        Farm farm = farmService.findById(id);
        return ResponseEntity.ok(farmMapper.toDTO(farm));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FarmDTO>> getAllFarms(Pageable pageable) {
        Page<Farm> farms = farmService.findAll(pageable);
        Page<FarmDTO> farmDTOs = farms.map(farmMapper::toDTO);
        return ResponseEntity.ok(farmDTOs);
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
        List<Farm> farms = farmService.searchFarms(name, location, minArea, maxArea, startDate, endDate);
        List<FarmDTO> farmDTOs = farms.stream().map(farmMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(farmDTOs);
    }
}