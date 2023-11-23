package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.CompanyAppeal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAppealDTO {
    private Long id;
    private String content;
    private String answer;
    private RegisteredUserDTO registeredUser;
    private CompanyDTO company;

    public CompanyAppealDTO(CompanyAppeal companyAppeal) {
        this.id = companyAppeal.getId();
        this.content = companyAppeal.getContent();
        this.answer = companyAppeal.getAnswer();
        this.registeredUser = new RegisteredUserDTO(companyAppeal.getRegisteredUser());
        this.company = new CompanyDTO(companyAppeal.getCompany());
    }
}
