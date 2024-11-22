package com.example.citronix.repository;

import com.example.citronix.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFarmId(Long farmId);
    long countByFarmId(Long farmId);
}
