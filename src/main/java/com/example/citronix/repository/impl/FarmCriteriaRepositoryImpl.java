package com.example.citronix.repository.impl;

import com.example.citronix.domain.Farm;
import com.example.citronix.repository.FarmCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FarmCriteriaRepositoryImpl implements FarmCriteriaRepository {
    private final EntityManager entityManager;

    public FarmCriteriaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> query = cb.createQuery(Farm.class);
        Root<Farm> root = query.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        }
        if (location != null) {
            predicates.add(cb.like(root.get("location"), "%" + location + "%"));
        }
        if (minArea != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("area"), minArea));
        }
        if (maxArea != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("area"), maxArea));
        }
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), startDate));
        }
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), endDate));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
