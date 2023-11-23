package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.SystemAdministrator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemAdministratorDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String profession;
    private String companyInfo;
    private AddressDTO address;
    private boolean isActivated;

    public SystemAdministratorDTO(SystemAdministrator systemAdministrator) {
        this.id = systemAdministrator.getId();
        this.name = systemAdministrator.getName();
        this.surname = systemAdministrator.getSurname();
        this.email = systemAdministrator.getEmail();
        this.password = systemAdministrator.getPassword();
        this.phoneNumber = systemAdministrator.getPhoneNumber();
        this.profession = systemAdministrator.getProfession();
        this.companyInfo = systemAdministrator.getCompanyInfo();
        this.address = new AddressDTO(systemAdministrator.getAddress());
        this.isActivated = systemAdministrator.isActivated();
    }
}
