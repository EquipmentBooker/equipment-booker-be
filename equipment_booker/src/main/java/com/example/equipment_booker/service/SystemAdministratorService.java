package com.example.equipment_booker.service;

import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.SystemAdministrator;
import com.example.equipment_booker.model.Term;
import com.example.equipment_booker.repository.SystemAdministratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemAdministratorService {

    @Autowired
    private SystemAdministratorRepository systemAdministratorRepository;

    public SystemAdministrator save(SystemAdministrator systemAdministrator) {
        return systemAdministratorRepository.save(systemAdministrator);
    }
    public SystemAdministrator findByEmail(String email) {
        return systemAdministratorRepository.findByEmail(email);
    }
}
