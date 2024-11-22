package com.example.citronix.service;

import com.example.citronix.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SaleService {
    Sale save(Sale sale);
    Sale findById(Long id);
    Page<Sale> findAll(Pageable pageable);
    void delete(Long id);
}