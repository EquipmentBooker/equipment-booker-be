package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.model.Equipment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private AddressDTO address;
    private List<EquipmentDTO> equipment;

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.averageScore = company.getAverageScore();
        this.address = new AddressDTO(company.getAddress());
        this.equipment = company.getEquipment().stream().map(EquipmentDTO::new).collect(Collectors.toList());
    }
}
