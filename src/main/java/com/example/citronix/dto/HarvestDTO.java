package com.example.citronix.dto;

import com.example.citronix.domain.Season;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class HarvestDTO {
    private Season season;
    private LocalDate harvestDate;
    private Double totalQuantity;
    private List<HarvestDetailDTO> harvestDetails;
}
