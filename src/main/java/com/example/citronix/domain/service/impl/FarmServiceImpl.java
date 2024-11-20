package com.example.citronix.domain.service.impl;

import com.example.citronix.domain.Farm;
import com.example.citronix.domain.service.FarmService;
import com.example.citronix.domain.service.dto.FarmDTO;
import com.example.citronix.domain.service.dto.mapper.FarmMapper;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.web.errors.FieldMustBeNullException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    public FarmServiceImpl(FarmRepository farmRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
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
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with id: " + id));

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

}
