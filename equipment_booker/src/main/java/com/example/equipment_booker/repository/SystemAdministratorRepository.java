package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.SystemAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemAdministratorRepository extends JpaRepository<SystemAdministrator, Long> {
    SystemAdministrator findByEmail(String email);
}
