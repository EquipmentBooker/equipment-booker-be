package com.example.equipment_booker.model;

import com.example.equipment_booker.dto.CompanyDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="companies")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "average_score")
    private float averageScore;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Equipment> equipment = new ArrayList<>();

    public Company(CompanyDTO companyDTO) {
        this.id = companyDTO.getId();
        this.name = companyDTO.getName();
        this.description = companyDTO.getDescription();
        this.averageScore = companyDTO.getAverageScore();
        this.startTime = companyDTO.getStartTime();
        this.endTime = companyDTO.getEndTime();
        this.address = new Address(companyDTO.getAddress());
    }
}
