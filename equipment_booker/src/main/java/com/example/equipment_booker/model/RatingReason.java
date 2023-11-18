package com.example.equipment_booker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RatingReason {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating_item_id")
    private RatingItem item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_rating_id")
    private CompanyRating companyRating;
}
