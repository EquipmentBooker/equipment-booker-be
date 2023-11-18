package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.CompanyDTO;
import com.example.equipment_booker.dto.EquipmentDTO;
import com.example.equipment_booker.dto.PredefinedTermDTO;
import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.model.PredefinedTerm;
import com.example.equipment_booker.service.PredefinedTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/predefined_terms")
public class PredefinedTermController {

    @Autowired
    private PredefinedTermService predefinedTermService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PredefinedTermDTO> savePredefinedTerm(@RequestBody PredefinedTermDTO predefinedTermDTO) {
        PredefinedTerm predefinedTerm = new PredefinedTerm();
        predefinedTerm.setStartTime(LocalDateTime.now());
        predefinedTerm.setDuration(predefinedTermDTO.getDuration());
        predefinedTerm.setStatus("FREE");
        predefinedTerm.setCompanyAdministrator(new CompanyAdministrator(predefinedTermDTO.getCompanyAdministrator()));

        predefinedTerm = predefinedTermService.save(predefinedTerm);
        return new ResponseEntity<>(new PredefinedTermDTO(predefinedTerm), HttpStatus.CREATED);
    }

    @GetMapping(value = "/company/{companyId}")
    public ResponseEntity<List<PredefinedTermDTO>> getFreePredefinedTerms(@PathVariable Long companyId) {

        List<PredefinedTerm> predefinedTerms = predefinedTermService.findFreePredefinedTermsByCompanyId(companyId, LocalDateTime.now(), "FREE");

        List<PredefinedTermDTO> predefinedTermsDTO = new ArrayList<>();
        for (PredefinedTerm pt: predefinedTerms) {
            predefinedTermsDTO.add(new PredefinedTermDTO(pt));
        }

        return new ResponseEntity<>(predefinedTermsDTO, HttpStatus.OK);
    }
}
