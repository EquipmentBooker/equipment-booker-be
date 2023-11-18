package com.example.equipment_booker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Scale {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "points")
    private int points;

    @Column(name = "lower_limit_points")
    private int lowerLimitPoints;

    @Column(name = "upper_limit_points")
    private int upperLimitPoints;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loyalty_category_id", referencedColumnName = "id")
    private LoyaltyCategory category;
}
