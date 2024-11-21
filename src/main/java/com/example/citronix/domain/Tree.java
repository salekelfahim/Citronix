package com.example.citronix.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Getter
@Setter
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate plantingDate;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails;

    public int getAge() {
        if (plantingDate != null) {
            return Period.between(plantingDate, LocalDate.now()).getYears();
        }
        return 0;
    }

    public double getAnnualProductivity() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12;
        }
        return 0;
    }

    public boolean isProductive() {
        return getAge() <= 20;
    }

    public boolean isValidPlantingPeriod() {
        if (plantingDate != null) {
            int month = plantingDate.getMonthValue();
            return month >= 3 && month <= 5;
        }
        return false;
    }

}
