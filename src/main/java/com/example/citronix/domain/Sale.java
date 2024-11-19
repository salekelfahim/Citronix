package com.example.citronix.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate saleDate;
    private Double unitPrice;
    private Double quantity;
    private String client;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;

}
