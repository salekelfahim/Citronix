package com.example.citronix.domain.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDTO {
    private Long id;
    private String name;
    private Double area;
    private Long farmId;
}
