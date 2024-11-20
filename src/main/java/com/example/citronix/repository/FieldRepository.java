package com.example.citronix.repository;

import com.example.citronix.domain.Field;
import com.example.citronix.domain.service.dto.FieldDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFarmId(Long farmId);
    long countByFarmId(Long farmId);
}
