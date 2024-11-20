package com.example.citronix.repository;

import com.example.citronix.domain.Farm;

import java.time.LocalDate;
import java.util.List;

public interface FarmCriteriaRepository {
    List<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDate startDate, LocalDate endDate);
}
