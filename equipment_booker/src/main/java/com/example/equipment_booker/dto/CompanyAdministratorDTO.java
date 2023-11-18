package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.CompanyAdministrator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAdministratorDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String profession;
    private String companyInfo;
    private boolean isActivated;
    private AddressDTO address;
    private CompanyDTO company;

    public CompanyAdministratorDTO(CompanyAdministrator companyAdministrator) {
        this.id = companyAdministrator.getId();
        this.name = companyAdministrator.getName();
        this.surname = companyAdministrator.getSurname();
        this.email = companyAdministrator.getEmail();
        this.password = companyAdministrator.getPassword();
        this.phoneNumber = companyAdministrator.getPhoneNumber();
        this.profession = companyAdministrator.getProfession();
        this.companyInfo = companyAdministrator.getCompanyInfo();
        this.isActivated = companyAdministrator.isActivated();
        this.address = new AddressDTO(companyAdministrator.getAddress());
        this.company = new CompanyDTO(companyAdministrator.getCompany());
    }
}
