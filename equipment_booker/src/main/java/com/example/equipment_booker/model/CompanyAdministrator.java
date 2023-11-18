package com.example.equipment_booker.model;

import com.example.equipment_booker.dto.CompanyAdministratorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "company_administrators")
public class CompanyAdministrator extends User {

    @Column(name = "is_activated")
    private boolean isActivated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    public CompanyAdministrator() {

    }

    public CompanyAdministrator(CompanyAdministratorDTO companyAdministratorDTO) {
        super(companyAdministratorDTO);
        this.isActivated = companyAdministratorDTO.isActivated();
        this.company = new Company(companyAdministratorDTO.getCompany());
    }
}
