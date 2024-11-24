package com.example.citronix.repository;

import com.example.citronix.domain.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
}
