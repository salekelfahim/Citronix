package com.example.citronix.service.impl;

import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.domain.Season;
import com.example.citronix.service.HarvestService;
import com.example.citronix.repository.HarvestDetailRepository;
import com.example.citronix.repository.HarvestRepository;
import com.example.citronix.web.errors.SeasonLimitException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestDetailRepository harvestDetailRepository;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestDetailRepository harvestDetailRepository) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailRepository = harvestDetailRepository;
    }

    @Override
    @Transactional
    public Harvest createHarvest(Harvest harvest) {
        if (!validateHarvestSeason(harvest.getHarvestDate(), harvest.getSeason())) {
            throw new IllegalArgumentException("Invalid harvest season for date: " + harvest.getHarvestDate());
        }

        if (harvest.getHarvestDetails() != null) {
            for (HarvestDetail detail : harvest.getHarvestDetails()) {
                if (!validateTreeHarvest(detail.getTree().getId(), harvest.getSeason(), harvest.getHarvestDate())) {
                    throw new SeasonLimitException(
                            "Tree ID " + detail.getTree().getId() + " has already been harvested in " +
                                    harvest.getSeason() + " season"
                    );
                }
            }
        }

        Harvest savedHarvest = harvestRepository.save(harvest);

        if (harvest.getHarvestDetails() != null) {
            double totalQuantity = 0.0;

            for (HarvestDetail detail : harvest.getHarvestDetails()) {
                detail.setHarvest(savedHarvest);
                detail.setQuantity(detail.getTree().getAnnualProductivity());
                harvestDetailRepository.save(detail);
                totalQuantity += detail.getQuantity();
            }

            savedHarvest.setTotalQuantity(totalQuantity);
            savedHarvest = harvestRepository.save(savedHarvest);
        }

        return savedHarvest;
    }


    @Override
    @Transactional
    public HarvestDetail addHarvestDetail(Long harvestId, HarvestDetail detail) {
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));

        if (!validateTreeHarvest(detail.getTree().getId(), harvest.getSeason(), harvest.getHarvestDate())) {
            throw new SeasonLimitException("Tree already harvested this season");
        }

        detail.setQuantity(detail.getTree().getAnnualProductivity());
        detail.setHarvest(harvest);
        HarvestDetail savedDetail = harvestDetailRepository.save(detail);

        double newTotal = harvest.getTotalQuantity() + detail.getQuantity();
        harvest.setTotalQuantity(newTotal);
        harvestRepository.save(harvest);

        return savedDetail;
    }

    @Override
    public Page<Harvest> getAllHarvests(Pageable pageable) {
        return harvestRepository.findAll(pageable);
    }

    @Override
    public Harvest getHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Harvest not found with id: " + id));
    }

    @Override
    public void deleteHarvest(Long id) {
        if (!harvestRepository.existsById(id)) {
            throw new EntityNotFoundException("Harvest not found with id: " + id);
        }
        harvestRepository.deleteById(id);
    }

    @Override
    public boolean validateHarvestSeason(LocalDate harvestDate, Season season) {
        int month = harvestDate.getMonthValue();
        switch (season) {
            case WINTER:
                return (month == 12 || month == 1 || month == 2);
            case SPRING:
                return (month == 3 || month == 4 || month == 5);
            case SUMMER:
                return (month == 6 || month == 7 || month == 8);
            case AUTUMN:
                return (month == 9 || month == 10 || month == 11);
            default:
                return false;
        }
    }


    @Override
    public boolean validateTreeHarvest(Long treeId, Season season, LocalDate harvestDate) {
        int year = harvestDate.getYear();
        LocalDate seasonStart;
        LocalDate seasonEnd;

        switch (season) {
            case WINTER:
                if (harvestDate.getMonthValue() <= 2) {
                    seasonStart = LocalDate.of(year - 1, 12, 1);
                    seasonEnd = LocalDate.of(year, 2, 28);
                } else {
                    seasonStart = LocalDate.of(year, 12, 1);
                    seasonEnd = LocalDate.of(year + 1, 2, 28);
                }
                break;
            case SPRING:
                seasonStart = LocalDate.of(year, 3, 1);
                seasonEnd = LocalDate.of(year, 5, 31);
                break;
            case SUMMER:
                seasonStart = LocalDate.of(year, 6, 1);
                seasonEnd = LocalDate.of(year, 8, 31);
                break;
            case AUTUMN:
                seasonStart = LocalDate.of(year, 9, 1);
                seasonEnd = LocalDate.of(year, 11, 30);
                break;
            default:
                throw new IllegalArgumentException("Invalid season");
        }

        List<HarvestDetail> existingHarvests = harvestDetailRepository.findByTreeAndSeasonInPeriod(
                treeId,
                season,
                seasonStart,
                seasonEnd
        );

        return existingHarvests.isEmpty();
    }
}


