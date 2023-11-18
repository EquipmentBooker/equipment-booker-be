package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private Long id;
    private String name;
    private String description;
    private float averageScore;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
    private AddressDTO address;
    private List<EquipmentDTO> equipment;

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.averageScore = company.getAverageScore();
        this.startTime = company.getStartTime();
        this.endTime = company.getEndTime();
        this.address = new AddressDTO(company.getAddress());
        this.equipment = company.getEquipment().stream().map(EquipmentDTO::new).collect(Collectors.toList());
    }
}
