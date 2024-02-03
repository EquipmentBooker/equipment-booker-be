package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
