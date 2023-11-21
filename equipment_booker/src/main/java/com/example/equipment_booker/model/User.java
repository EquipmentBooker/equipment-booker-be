package com.example.equipment_booker.model;

import com.example.equipment_booker.dto.CompanyAdministratorDTO;
import com.example.equipment_booker.dto.RegisteredUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "mySeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGen")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profession")
    private String profession;

    @Column(name = "company_info")
    private String companyInfo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    public User(CompanyAdministratorDTO companyAdministratorDTO) {
        this.id = companyAdministratorDTO.getId();
        this.name = companyAdministratorDTO.getName();
        this.surname = companyAdministratorDTO.getSurname();
        this.email = companyAdministratorDTO.getEmail();
        this.password = companyAdministratorDTO.getPassword();
        this.phoneNumber = companyAdministratorDTO.getPhoneNumber();
        this.profession = companyAdministratorDTO.getProfession();
        this.companyInfo = companyAdministratorDTO.getCompanyInfo();
        this.address = new Address(companyAdministratorDTO.getAddress());
    }

    public User(RegisteredUserDTO registeredUser) {
        this.id = registeredUser.getId();
        this.name = registeredUser.getName();
        this.surname = registeredUser.getSurname();
        this.email = registeredUser.getEmail();
        this.password = registeredUser.getPassword();
        this.phoneNumber = registeredUser.getPhoneNumber();
        this.profession = registeredUser.getProfession();
        this.companyInfo = registeredUser.getCompanyInfo();
        this.address = new Address(registeredUser.getAddress());
    }
}
