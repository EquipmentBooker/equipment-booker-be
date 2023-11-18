package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.CompanyAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAdministratorRepository extends JpaRepository<CompanyAdministrator, Long> {
    CompanyAdministrator findByEmail(String email);
}
