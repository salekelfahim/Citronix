package com.example.citronix.service.impl;

import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.Sale;
import com.example.citronix.repository.HarvestRepository;
import com.example.citronix.repository.SaleRepository;
import com.example.citronix.service.SaleService;
import com.example.citronix.web.errors.InsufficientHarvestQuantityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;

    public SaleServiceImpl(SaleRepository saleRepository, HarvestRepository harvestRepository) {
        this.saleRepository = saleRepository;
        this.harvestRepository = harvestRepository;
    }

    @Override
    public Sale save(Sale sale) {
        Harvest harvest = harvestRepository.findById(sale.getHarvest().getId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest not found with id: " + sale.getHarvest().getId()));

        if (harvest.getTotalQuantity() < sale.getQuantity()) {
            throw new InsufficientHarvestQuantityException();
        }

        harvest.setTotalQuantity(harvest.getTotalQuantity() - sale.getQuantity());
        harvestRepository.save(harvest);

        return saleRepository.save(sale);
    }

    @Override
    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found with id: " + id));
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new EntityNotFoundException("Sale not found with id: " + id);
        }
        saleRepository.deleteById(id);
    }
}
