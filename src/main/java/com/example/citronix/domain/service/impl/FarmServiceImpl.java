package com.example.citronix.domain.service.impl;

import com.example.citronix.domain.Farm;
import com.example.citronix.domain.service.FarmService;
import com.example.citronix.domain.service.dto.FarmDTO;
import com.example.citronix.domain.service.dto.mapper.FarmMapper;
import com.example.citronix.repository.FarmCriteriaRepository;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.web.errors.FarmNotFoundException;
import com.example.citronix.web.errors.FieldMustBeNullException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmCriteriaRepository farmCriteriaRepository;

    private final FarmMapper farmMapper;

    public FarmServiceImpl(FarmRepository farmRepository,FarmCriteriaRepository farmCriteriaRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmCriteriaRepository = farmCriteriaRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public FarmDTO save(FarmDTO farmDTO) {
        if (farmDTO.getFields() != null && !farmDTO.getFields().isEmpty()) {
            throw new FieldMustBeNullException();
        }

        Farm farm = farmMapper.toEntity(farmDTO);
        farm = farmRepository.save(farm);
        return farmMapper.toDTO(farm);
    }

    @Override
    public FarmDTO update(Long id, FarmDTO farmDTO) {
        Farm existingFarm = farmRepository.findById(id)
                .orElseThrow(FarmNotFoundException::new);

        existingFarm.setName(farmDTO.getName());
        existingFarm.setLocation(farmDTO.getLocation());
        existingFarm.setArea(farmDTO.getArea());
        existingFarm.setCreationDate(farmDTO.getCreationDate());

        Farm updatedFarm = farmRepository.save(existingFarm);
        return farmMapper.toDTO(updatedFarm);
    }

    @Override
    public FarmDTO findById(Long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with id: " + id));
        return farmMapper.toDTO(farm);
    }

    @Override
    public Page<FarmDTO> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable)
                .map(farmMapper::toDTO);
    }

    @Override
    public List<FarmDTO> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDate startDate, LocalDate endDate) {
        List<Farm> farms = farmCriteriaRepository.searchFarms(name, location, minArea, maxArea, startDate, endDate);
        return farms.stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }

}
