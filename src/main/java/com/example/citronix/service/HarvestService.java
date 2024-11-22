package com.example.citronix.service;

import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.domain.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface HarvestService {
    Harvest createHarvest(Harvest harvest);
    HarvestDetail addHarvestDetail(Long harvestId, HarvestDetail detail);
    Page<Harvest> getAllHarvests(Pageable pageable);
    Harvest getHarvestById(Long id);
    void deleteHarvest(Long id);
    boolean validateHarvestSeason(LocalDate harvestDate, Season season);
    boolean validateTreeHarvest(Long treeId, Season season, LocalDate harvestDate);
}
