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

    public FarmServiceImpl(FarmRepository farmRepository, FarmCriteriaRepository farmCriteriaRepository) {
        this.farmRepository = farmRepository;
        this.farmCriteriaRepository = farmCriteriaRepository;
    }

    @Override
    public Farm save(Farm farm) {
        if (farm.getFields() != null && !farm.getFields().isEmpty()) {
            throw new FieldMustBeNullException();
        }
        return farmRepository.save(farm);
    }

    @Override
    public Farm update(Long id, Farm farm) {
        Farm existingFarm = farmRepository.findById(id)
                .orElseThrow(FarmNotFoundException::new);

        existingFarm.setName(farm.getName());
        existingFarm.setLocation(farm.getLocation());
        existingFarm.setArea(farm.getArea());
        existingFarm.setCreationDate(farm.getCreationDate());

        return farmRepository.save(existingFarm);
    }

    @Override
    public Farm findById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(FarmNotFoundException::new);
    }

    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable);
    }

    @Override
    public List<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDate startDate, LocalDate endDate) {
        return farmCriteriaRepository.searchFarms(name, location, minArea, maxArea, startDate, endDate);
    }
}
