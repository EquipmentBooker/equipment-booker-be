package com.example.equipment_booker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoyaltyProgram {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "points")
    private int points;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loyalty_category_id", referencedColumnName = "id")
    private LoyaltyCategory category;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registered_user_id", referencedColumnName = "id")
    private RegisteredUser registeredUser;
}
