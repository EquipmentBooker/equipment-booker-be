package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.Address;
import com.example.equipment_booker.model.RegisteredUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String profession;
    private String companyInfo;
    private String confirmationToken;
    private boolean isActivated;
    private AddressDTO address;

    public RegisteredUserDTO(RegisteredUser registeredUser) {
        this.id = registeredUser.getId();
        this.name = registeredUser.getName();
        this.surname = registeredUser.getSurname();
        this.email = registeredUser.getEmail();
        this.password = registeredUser.getPassword();
        this.phoneNumber = registeredUser.getPhoneNumber();
        this.profession = registeredUser.getProfession();
        this.companyInfo = registeredUser.getCompanyInfo();
        this.confirmationToken = registeredUser.getConfirmationToken();
        this.isActivated = registeredUser.isActivated();
        this.address = new AddressDTO(registeredUser.getAddress());
    }
}
