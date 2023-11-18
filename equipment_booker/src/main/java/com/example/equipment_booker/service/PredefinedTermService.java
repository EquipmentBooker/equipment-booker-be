package com.example.equipment_booker.service;

import com.example.equipment_booker.model.PredefinedTerm;
import com.example.equipment_booker.repository.PredefinedTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredefinedTermService {

    @Autowired
    private PredefinedTermRepository predefinedTermRepository;

    public PredefinedTerm save(PredefinedTerm predefinedTerm) {
        return predefinedTermRepository.save(predefinedTerm);
    }

    public List<PredefinedTerm> findFreePredefinedTermsByCompanyId(Long companyId, LocalDateTime currentDateTime, String status) {
        return predefinedTermRepository.findFreePredefinedTermsByCompanyId(companyId, currentDateTime, status);
    }
}
