package com.example.citronix.repository;

import com.example.citronix.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
    int countByFieldId(Long fieldId);
}
