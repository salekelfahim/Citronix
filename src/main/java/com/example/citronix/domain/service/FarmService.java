package com.example.citronix.domain.service;


import com.example.citronix.domain.service.dto.FarmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface FarmService {
    FarmDTO save(FarmDTO farmDTO);
    FarmDTO update(Long id, FarmDTO farmDTO);
    FarmDTO findById(Long id);
    Page<FarmDTO> findAll(Pageable pageable);
    List<FarmDTO> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDate startDate, LocalDate endDate);


}
