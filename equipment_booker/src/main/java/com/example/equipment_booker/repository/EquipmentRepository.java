package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
