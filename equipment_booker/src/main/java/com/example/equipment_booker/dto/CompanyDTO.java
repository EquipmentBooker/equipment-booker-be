package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.Address;
import com.example.equipment_booker.model.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.averageScore = company.getAverageScore();
        this.address = new AddressDTO(company.getAddress());
    }
}
