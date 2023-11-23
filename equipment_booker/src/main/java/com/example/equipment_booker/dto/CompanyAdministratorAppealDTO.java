package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.CompanyAdministratorAppeal;
import com.example.equipment_booker.model.RegisteredUser;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAdministratorAppealDTO {
    private Long id;
    private String content;
    private String answer;
    private RegisteredUserDTO registeredUser;
    private CompanyAdministratorDTO companyAdministrator;

    public CompanyAdministratorAppealDTO(CompanyAdministratorAppeal companyAdministratorAppeal) {
        this.id = companyAdministratorAppeal.getId();
        this.content = companyAdministratorAppeal.getContent();
        this.answer = companyAdministratorAppeal.getAnswer();
        this.registeredUser = new RegisteredUserDTO(companyAdministratorAppeal.getRegisteredUser());
        this.companyAdministrator = new CompanyAdministratorDTO(companyAdministratorAppeal.getCompanyAdministrator());
    }
}
