package com.example.citronix.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TreeDTO {
    private Long id;
    private LocalDate plantingDate;
    private Long fieldId;

}
