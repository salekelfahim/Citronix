package com.example.citronix.repository;

import com.example.citronix.domain.Farm;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FarmCriteriaRepository {
    List<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDate startDate, LocalDate endDate);
}
