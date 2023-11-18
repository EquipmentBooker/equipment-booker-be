package com.example.equipment_booker.service;

import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.repository.CompanyAdministratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyAdministratorService {
    @Autowired
    private CompanyAdministratorRepository companyAdministratorRepository;

    public List<CompanyAdministrator> findAll() {
        return companyAdministratorRepository.findAll();
    }
    public CompanyAdministrator save(CompanyAdministrator companyAdministrator) {
        return companyAdministratorRepository.save(companyAdministrator);
    }
    public CompanyAdministrator findByEmail(String email) {
        return companyAdministratorRepository.findByEmail(email);
    }
}
