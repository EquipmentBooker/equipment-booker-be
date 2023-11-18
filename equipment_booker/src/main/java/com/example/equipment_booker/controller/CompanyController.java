package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.CompanyDTO;
import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getCompanies() {

        List<Company> companies = companyService.findAll();

        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {

        Company company = companyService.findOne(id);

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CompanyDTO companyDTO = new CompanyDTO(company);

        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findNameOrLocation")
    public ResponseEntity<List<CompanyDTO>> findCompaniesByNameOrLocation(@RequestParam String searchValue) {

        List<Company> companies = companyService.findCompaniesByNameOrLocation(searchValue);

        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findNameOrLocationAndGrade")
    public ResponseEntity<List<CompanyDTO>> findCompaniesByNameOrLocationAndGrade(@RequestParam String searchValue, @RequestParam String filterGradeValue) {
        String[] grades = filterGradeValue.split(" - ");

        List<Company> companies = companyService.findCompaniesByNameOrLocationAndGrade(searchValue, Integer.parseInt(grades[0]), Integer.parseInt(grades[1]));

        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }
}
