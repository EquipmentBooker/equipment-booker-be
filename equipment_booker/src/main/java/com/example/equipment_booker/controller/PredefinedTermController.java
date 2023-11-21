package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.CompanyDTO;
import com.example.equipment_booker.dto.EquipmentDTO;
import com.example.equipment_booker.dto.PredefinedTermDTO;
import com.example.equipment_booker.dto.SchedulePredefinedTermDTO;
import com.example.equipment_booker.model.*;
import com.example.equipment_booker.service.*;
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
    @Autowired
    private TermService termService;
    @Autowired
    private RegisteredUserService registeredUserService;
    @Autowired
    private TermEquipmentService termEquipmentService;
    @Autowired
    private EquipmentService equipmentService;

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

    @PutMapping(value = "/schedule/{predefinedTermId}", consumes = "application/json")
    public ResponseEntity<PredefinedTermDTO> schedulePredefinedTerm(@RequestBody SchedulePredefinedTermDTO schedulePredefinedTermDTO, @PathVariable Long predefinedTermId) throws Exception {

        PredefinedTerm predefinedTerm = predefinedTermService.findOne(schedulePredefinedTermDTO.getPredefinedTerm().getId());

        if (predefinedTerm == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        predefinedTerm.setStatus("SCHEDULED");

        predefinedTerm = predefinedTermService.save(predefinedTerm);

        Term term = new Term();
        term.setDuration(schedulePredefinedTermDTO.getPredefinedTerm().getDuration());
        term.setStartTime(schedulePredefinedTermDTO.getPredefinedTerm().getStartTime());
        term.setStatus("SCHEDULED");
        term.setPredefined(true);
        term.setCompanyAdministrator(new CompanyAdministrator(schedulePredefinedTermDTO.getPredefinedTerm().getCompanyAdministrator()));
        term.setRegisteredUser(registeredUserService.findOne(schedulePredefinedTermDTO.getRegisteredUserId()));

        term = termService.save(term);

        for (EquipmentDTO e: schedulePredefinedTermDTO.getReservedEquipment()) {
            TermEquipment termEquipment = new TermEquipment();
            termEquipment.setQuantity(e.getQuantity());
            termEquipment.setTerm(term);
            termEquipment.setEquipment(equipmentService.findOne(e.getId()));

            termEquipment = termEquipmentService.save(termEquipment);
        }

        predefinedTermService.sendReservationEmail(registeredUserService.findOne(schedulePredefinedTermDTO.getRegisteredUserId()), termService.findOne(term.getId()), termEquipmentService.findByTermId(term.getId()));

        return new ResponseEntity<>(new PredefinedTermDTO(predefinedTerm), HttpStatus.OK);
    }
}
