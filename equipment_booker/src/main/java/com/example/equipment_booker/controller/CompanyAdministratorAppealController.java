package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.CompanyAdministratorAppealDTO;
import com.example.equipment_booker.dto.CompanyAppealDTO;
import com.example.equipment_booker.model.CompanyAdministratorAppeal;
import com.example.equipment_booker.model.CompanyAppeal;
import com.example.equipment_booker.service.CompanyAdministratorAppealService;
import com.example.equipment_booker.service.CompanyAdministratorService;
import com.example.equipment_booker.service.RegisteredUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/company_administrator_appeals")
public class CompanyAdministratorAppealController {
    @Autowired
    private CompanyAdministratorAppealService companyAdministratorAppealService;
    @Autowired
    private RegisteredUserService registeredUserService;
    @Autowired
    private CompanyAdministratorService companyAdministratorService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CompanyAdministratorAppealDTO> saveCompanyAdministratorAppeal(@RequestBody CompanyAdministratorAppealDTO companyAdministratorAppealDTO) {

        CompanyAdministratorAppeal companyAdministratorAppeal = new CompanyAdministratorAppeal();
        companyAdministratorAppeal.setContent(companyAdministratorAppealDTO.getContent());
        companyAdministratorAppeal.setAnswer(companyAdministratorAppealDTO.getAnswer());
        companyAdministratorAppeal.setRegisteredUser(registeredUserService.findOne(companyAdministratorAppealDTO.getRegisteredUser().getId()));
        companyAdministratorAppeal.setCompanyAdministrator(companyAdministratorService.findOne(companyAdministratorAppealDTO.getCompanyAdministrator().getId()));

        companyAdministratorAppeal = companyAdministratorAppealService.save(companyAdministratorAppeal);
        return new ResponseEntity<>(new CompanyAdministratorAppealDTO(companyAdministratorAppeal), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CompanyAdministratorAppealDTO>> getCompanyAdministratorAppeals() {

        List<CompanyAdministratorAppeal> companyAdministratorAppeals = companyAdministratorAppealService.findAll();

        List<CompanyAdministratorAppealDTO> companyAdministratorAppealsDTO = new ArrayList<>();
        for (CompanyAdministratorAppeal caa: companyAdministratorAppeals) {
            companyAdministratorAppealsDTO.add(new CompanyAdministratorAppealDTO(caa));
        }

        return new ResponseEntity<>(companyAdministratorAppealsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{companyAdministratorAppealId}/answer", consumes = "application/json")
    public ResponseEntity<CompanyAdministratorAppealDTO> createAnswerToAppeal(@RequestBody CompanyAdministratorAppealDTO companyAdministratorAppealDTO, @PathVariable Long companyAdministratorAppealId) {

        CompanyAdministratorAppeal companyAdministratorAppeal = companyAdministratorAppealService.findOne(companyAdministratorAppealId);

        if (companyAdministratorAppeal == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        try {
            companyAdministratorAppeal.setAnswer(companyAdministratorAppealDTO.getAnswer());
            companyAdministratorAppeal = companyAdministratorAppealService.update(companyAdministratorAppeal);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        companyAdministratorAppealService.sendEmailWithAnswer(companyAdministratorAppeal);
        return new ResponseEntity<>(new CompanyAdministratorAppealDTO(companyAdministratorAppeal), HttpStatus.OK);
    }

    @GetMapping(value = "/registeredUser/{registeredUserId}")
    public ResponseEntity<List<CompanyAdministratorAppealDTO>> getRegisteredUserAppeals(@PathVariable Long registeredUserId) {

        List<CompanyAdministratorAppeal> companyAdministratorAppeals = companyAdministratorAppealService.findAppealsByRegisteredUserId(registeredUserId);

        List<CompanyAdministratorAppealDTO> companyAdministratorAppealsDTO = new ArrayList<>();
        for (CompanyAdministratorAppeal caa: companyAdministratorAppeals) {
            companyAdministratorAppealsDTO.add(new CompanyAdministratorAppealDTO(caa));
        }

        return new ResponseEntity<>(companyAdministratorAppealsDTO, HttpStatus.OK);
    }
}
