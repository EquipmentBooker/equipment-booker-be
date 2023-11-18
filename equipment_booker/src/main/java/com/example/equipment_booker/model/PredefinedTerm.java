package com.example.equipment_booker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="predefined_terms")
public class PredefinedTerm {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "duration")
    private int duration;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_administrator_id", referencedColumnName = "id")
    private CompanyAdministrator companyAdministrator;
}
