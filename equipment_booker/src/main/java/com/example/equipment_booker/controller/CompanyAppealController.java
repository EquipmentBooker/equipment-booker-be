package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.CompanyAdministratorDTO;
import com.example.equipment_booker.dto.CompanyAppealDTO;
import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.CompanyAppeal;
import com.example.equipment_booker.service.CompanyAppealService;
import com.example.equipment_booker.service.CompanyService;
import com.example.equipment_booker.service.RegisteredUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/appeals")
@SecurityRequirement(name = "bearerAuth")
public class CompanyAppealController {
    @Autowired
    private CompanyAppealService companyAppealService;
    @Autowired
    private RegisteredUserService registeredUserService;
    @Autowired
    private CompanyService companyService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CompanyAppealDTO> saveCompanyAppeal(@RequestBody CompanyAppealDTO companyAppealDTO) {

        CompanyAppeal companyAppeal = new CompanyAppeal();
        companyAppeal.setContent(companyAppealDTO.getContent());
        companyAppeal.setAnswer(companyAppealDTO.getAnswer());
        companyAppeal.setRegisteredUser(registeredUserService.findOne(companyAppealDTO.getRegisteredUser().getId()));
        companyAppeal.setCompany(companyService.findOne(companyAppealDTO.getCompany().getId()));

        companyAppeal = companyAppealService.save(companyAppeal);
        return new ResponseEntity<>(new CompanyAppealDTO(companyAppeal), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CompanyAppealDTO>> getCompanyAppeals() {

        List<CompanyAppeal> companyAppeals = companyAppealService.findAll();

        List<CompanyAppealDTO> companyAppealsDTO = new ArrayList<>();
        for (CompanyAppeal ca: companyAppeals) {
            companyAppealsDTO.add(new CompanyAppealDTO(ca));
        }

        return new ResponseEntity<>(companyAppealsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{companyAppealId}/answer", consumes = "application/json")
    public ResponseEntity<CompanyAppealDTO> createAnswerToAppeal(@RequestBody CompanyAppealDTO companyAppealDTO, @PathVariable Long companyAppealId) {

        CompanyAppeal companyAppeal = companyAppealService.findOne(companyAppealId);

        if (companyAppeal == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        companyAppeal.setAnswer(companyAppealDTO.getAnswer());

        companyAppeal = companyAppealService.save(companyAppeal);

        companyAppealService.sendEmailWithAnswer(companyAppeal);
        return new ResponseEntity<>(new CompanyAppealDTO(companyAppeal), HttpStatus.OK);
    }
}
