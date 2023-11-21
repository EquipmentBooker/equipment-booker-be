package com.example.equipment_booker.service;

import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.model.Term;
import com.example.equipment_booker.model.TermEquipment;
import com.example.equipment_booker.repository.TermEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermEquipmentService {
    @Autowired
    private TermEquipmentRepository termEquipmentRepository;

    public TermEquipment save(TermEquipment termEquipment) {
        return termEquipmentRepository.save(termEquipment);
    }
    public List<TermEquipment> findByTermId(Long termId) {
        return termEquipmentRepository.findTermEquipmentByTermId(termId);
    }
    public void remove(Long id) {
        termEquipmentRepository.deleteById(id);
    }
}
