package com.example.equipment_booker.service;

import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }
    public Equipment findOne(Long id) {
        return equipmentRepository.findById(id).orElseGet(null);
    }
}
