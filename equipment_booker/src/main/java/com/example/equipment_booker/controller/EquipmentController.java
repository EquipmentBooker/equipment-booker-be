package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.CompanyDTO;
import com.example.equipment_booker.dto.EquipmentDTO;
import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.service.CompanyService;
import com.example.equipment_booker.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getEquipment() {

        List<Equipment> equipment = equipmentService.findAll();

        List<EquipmentDTO> equipmentDTO = new ArrayList<>();
        for (Equipment e : equipment) {
            EquipmentDTO eqDTO = new EquipmentDTO(e);
            eqDTO.setCompany(new CompanyDTO(companyService.findOne(e.getCompany().getId())));
            equipmentDTO.add(eqDTO);
        }

        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }
}
