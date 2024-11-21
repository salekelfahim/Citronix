package com.example.citronix.domain.service;


import com.example.citronix.domain.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface FarmService {
    Farm save(Farm farm);
    Farm update(Long id, Farm farm);
    Farm findById(Long id);
    Page<Farm> findAll(Pageable pageable);
    List<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDate startDate, LocalDate endDate);
}
