package com.example.equipment_booker.service;

import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.model.PredefinedTerm;
import com.example.equipment_booker.model.Term;
import com.example.equipment_booker.model.TermEquipment;
import com.example.equipment_booker.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TermService {

    @Autowired
    private TermRepository termRepository;

    public Term save(Term term) {
        return termRepository.save(term);
    }
    public Term findOne(Long id) {
        return termRepository.findById(id).orElseGet(null);
    }
    public List<Term> findScheduledTermsByRegisteredUserId(Long registeredUserId, LocalDateTime currentTime, String status) {
        return termRepository.findScheduledTermsByRegisteredUserId(registeredUserId, currentTime, status);
    }
}
