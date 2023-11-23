package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.CompanyAdministratorDTO;
import com.example.equipment_booker.dto.SystemAdministratorDTO;
import com.example.equipment_booker.model.Address;
import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.SystemAdministrator;
import com.example.equipment_booker.service.SystemAdministratorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/system_administrators")
public class SystemAdministratorController {

    @Autowired
    private SystemAdministratorService systemAdministratorService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<SystemAdministratorDTO> saveSystemAdministrator(@RequestBody SystemAdministratorDTO systemAdministratorDTO) {

        SystemAdministrator systemAdministrator = new SystemAdministrator();
        systemAdministrator.setName(systemAdministratorDTO.getName());
        systemAdministrator.setSurname(systemAdministratorDTO.getSurname());
        systemAdministrator.setEmail(systemAdministratorDTO.getEmail());
        systemAdministrator.setPassword(passwordEncoder.encode(systemAdministratorDTO.getPassword()));
        systemAdministrator.setPhoneNumber(systemAdministratorDTO.getPhoneNumber());
        systemAdministrator.setCompanyInfo(systemAdministratorDTO.getCompanyInfo());
        systemAdministrator.setProfession(systemAdministratorDTO.getProfession());
        systemAdministrator.setAddress(new Address(systemAdministratorDTO.getAddress()));
        systemAdministrator.setActivated(systemAdministratorDTO.isActivated());

        systemAdministrator = systemAdministratorService.save(systemAdministrator);
        return new ResponseEntity<>(new SystemAdministratorDTO(systemAdministrator), HttpStatus.CREATED);
    }

    @GetMapping(value = "/email/{email}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<SystemAdministratorDTO> getSystemAdministratorByEmail(@PathVariable String email) {
        SystemAdministrator systemAdministrator = systemAdministratorService.findByEmail(email);

        if (systemAdministrator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new SystemAdministratorDTO(systemAdministrator), HttpStatus.OK);
    }
}
