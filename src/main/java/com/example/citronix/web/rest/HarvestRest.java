package com.example.citronix.web.rest;

import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.dto.HarvestDTO;
import com.example.citronix.dto.mapper.HarvestMapper;
import com.example.citronix.service.HarvestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/harvests")
public class HarvestRest {

    private final HarvestService harvestService;
    private final HarvestMapper harvestMapper;

    public HarvestRest(HarvestService harvestService, HarvestMapper harvestMapper) {
        this.harvestService = harvestService;
        this.harvestMapper = harvestMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<HarvestDTO> createHarvest(@RequestBody HarvestDTO harvestDTO) {
        Harvest harvest = harvestMapper.toEntity(harvestDTO);
        Harvest savedHarvest = harvestService.createHarvest(harvest);
        return ResponseEntity.ok(harvestMapper.toDto(savedHarvest));
    }

    @PostMapping("/{harvestId}/details")
    public ResponseEntity<HarvestDetail> addHarvestDetail(
            @PathVariable Long harvestId,
            @RequestBody HarvestDetail detail) {
        return ResponseEntity.ok(harvestService.addHarvestDetail(harvestId, detail));
    }

    @GetMapping
    public ResponseEntity<Page<Harvest>> getAllHarvests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Harvest> harvests = harvestService.getAllHarvests(pageable);

        return ResponseEntity.ok(harvests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Harvest> getHarvestById(@PathVariable Long id) {
        return ResponseEntity.ok(harvestService.getHarvestById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.ok().build();
    }
}
