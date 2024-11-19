package com.example.citronix.domain.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FarmDTO {
    private Long id;
    private String name;
    private String location;
    private Double area;
    private LocalDate creationDate;
    private List<String> fields;
}