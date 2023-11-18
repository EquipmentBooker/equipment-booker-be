package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.CompanyAdministratorDTO;
import com.example.equipment_booker.dto.RegisteredUserDTO;
import com.example.equipment_booker.model.Address;
import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.service.CompanyAdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/company_administrators")
public class CompanyAdministratorController {

    @Autowired
    private CompanyAdministratorService companyAdministratorService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<CompanyAdministratorDTO>> getCompanyAdministrators() {

        List<CompanyAdministrator> companyAdministrators = companyAdministratorService.findAll();

        List<CompanyAdministratorDTO> companyAdministratorsDTO = new ArrayList<>();
        for (CompanyAdministrator ca: companyAdministrators) {
            companyAdministratorsDTO.add(new CompanyAdministratorDTO(ca));
        }

        return new ResponseEntity<>(companyAdministratorsDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CompanyAdministratorDTO> saveCompanyAdministrator(@RequestBody CompanyAdministratorDTO companyAdministratorDTO) {

        CompanyAdministrator companyAdministrator = new CompanyAdministrator();
        companyAdministrator.setName(companyAdministratorDTO.getName());
        companyAdministrator.setSurname(companyAdministratorDTO.getSurname());
        companyAdministrator.setEmail(companyAdministratorDTO.getEmail());
        companyAdministrator.setPassword(passwordEncoder.encode(companyAdministratorDTO.getPassword()));
        companyAdministrator.setPhoneNumber(companyAdministratorDTO.getPhoneNumber());
        companyAdministrator.setCompanyInfo(companyAdministratorDTO.getCompanyInfo());
        companyAdministrator.setProfession(companyAdministratorDTO.getProfession());
        companyAdministrator.setAddress(new Address(companyAdministratorDTO.getAddress()));
        companyAdministrator.setActivated(companyAdministratorDTO.isActivated());
        companyAdministrator.setCompany(new Company(companyAdministratorDTO.getCompany()));

        companyAdministrator = companyAdministratorService.save(companyAdministrator);
        return new ResponseEntity<>(new CompanyAdministratorDTO(companyAdministrator), HttpStatus.CREATED);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<CompanyAdministratorDTO> getCompanyAdministratorByEmail(@PathVariable String email) {
        CompanyAdministrator companyAdministrator = companyAdministratorService.findByEmail(email);

        if (companyAdministrator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CompanyAdministratorDTO companyAdministratorDTO = new CompanyAdministratorDTO(companyAdministrator);

        return new ResponseEntity<>(companyAdministratorDTO, HttpStatus.OK);
    }
}
