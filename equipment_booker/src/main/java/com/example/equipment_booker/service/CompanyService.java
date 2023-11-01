package com.example.equipment_booker.service;

import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findOne(Long id) {
        return companyRepository.findById(id).orElseGet(null);
    }

    public List<Company> findCompaniesByNameOrLocation(String searchValue) {
        return companyRepository.findCompaniesByNameOrLocation(searchValue);
    }

    public List<Company> findCompaniesByNameOrLocationAndGrade(String searchValue, int lowerGrade, int higherGrade) {
        return companyRepository.findCompaniesByNameOrLocationAndGrade(searchValue, lowerGrade, higherGrade);
    }
}
