package com.example.equipment_booker.service;

import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.model.Term;
import com.example.equipment_booker.model.TermEquipment;
import com.example.equipment_booker.repository.TermEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermEquipmentService {
    @Autowired
    private TermEquipmentRepository termEquipmentRepository;
    @Transactional(readOnly = false)
    public TermEquipment save(TermEquipment termEquipment) {
        return termEquipmentRepository.save(termEquipment);
    }
    public List<TermEquipment> findByTermId(Long termId) {
        return termEquipmentRepository.findTermEquipmentByTermId(termId);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void remove(Long id) {
        termEquipmentRepository.deleteById(id);
    }
}
