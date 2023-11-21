package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.*;
import com.example.equipment_booker.model.*;
import com.example.equipment_booker.service.EquipmentService;
import com.example.equipment_booker.service.RegisteredUserService;
import com.example.equipment_booker.service.TermEquipmentService;
import com.example.equipment_booker.service.TermService;
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
@RequestMapping(value = "api/terms")
public class TermController {

    @Autowired
    private TermService termService;
    @Autowired
    private TermEquipmentService termEquipmentService;
    @Autowired
    private RegisteredUserService registeredUserService;
    @Autowired
    private EquipmentService equipmentService;

    @GetMapping(value = "/scheduled/registered-user/{registeredUserId}")
    public ResponseEntity<List<TermDTO>> getScheduledTermsByRegisteredUserId(@PathVariable Long registeredUserId) {

        List<Term> terms = termService.findScheduledTermsByRegisteredUserId(registeredUserId, LocalDateTime.now(), "SCHEDULED");

        List<TermDTO> termsDTO = new ArrayList<>();
        for (Term t: terms) {
            termsDTO.add(new TermDTO(t));
        }

        return new ResponseEntity<>(termsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/cancel/{termId}", consumes = "application/json")
    public ResponseEntity<TermDTO> cancelScheduledTerm(@RequestBody UpdateTermDTO updateTermDTO, @PathVariable Long termId) throws Exception {

        Term term = termService.findOne(termId);

        if (term == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        term.setStatus(updateTermDTO.getStatus());

        for (TermEquipmentDTO te: updateTermDTO.getTermEquipment()) {
            termEquipmentService.remove(te.getId());
        }

        RegisteredUser registeredUser = registeredUserService.findOne(updateTermDTO.getRegisteredUser().getId());
        if (LocalDateTime.now().plusHours(24).isEqual(term.getStartTime())
                || LocalDateTime.now().plusHours(24).isAfter(term.getStartTime())) {
            registeredUser.setPenalties(registeredUser.getPenalties() + 2);
        } else {
            registeredUser.setPenalties(registeredUser.getPenalties() + 1);
        }

        registeredUser = registeredUserService.save(registeredUser);

        term = termService.save(term);
        return new ResponseEntity<>(new TermDTO(term), HttpStatus.OK);
    }

    @GetMapping(value = "/free/company/{companyId}")
    public ResponseEntity<List<TermDTO>> getFreeTermsByCompanyId(@PathVariable Long companyId) {
        List<Term> terms = termService.findFreeTermsByCompanyId(companyId, LocalDateTime.now(), "FREE", true);

        List<TermDTO> termsDTO = new ArrayList<>();
        for (Term t: terms) {
            termsDTO.add(new TermDTO(t));
        }

        return new ResponseEntity<>(termsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/schedule/{termId}", consumes = "application/json")
    public ResponseEntity<TermDTO> scheduleFreeTerm(@RequestBody ScheduleTermDTO scheduleTermDTO, @PathVariable Long termId) throws Exception {

        Term term = termService.findOne(termId);

        if (term == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        term.setStatus("SCHEDULED");
        term.setRegisteredUser(registeredUserService.findOne(scheduleTermDTO.getRegisteredUserId()));

        for (EquipmentDTO e: scheduleTermDTO.getReservedEquipment()) {
            TermEquipment termEquipment = new TermEquipment();
            termEquipment.setQuantity(e.getQuantity());
            termEquipment.setTerm(term);
            termEquipment.setEquipment(equipmentService.findOne(e.getId()));

            termEquipment = termEquipmentService.save(termEquipment);
        }

        term = termService.save(term);

        termService.sendReservationEmail(registeredUserService.findOne(scheduleTermDTO.getRegisteredUserId()), termService.findOne(term.getId()), termEquipmentService.findByTermId(term.getId()));

        return new ResponseEntity<>(new TermDTO(term), HttpStatus.OK);
    }

    @GetMapping(value = "/past-scheduled/registered-user/{registeredUserId}")
    public ResponseEntity<List<TermDTO>> getPastScheduledTermsByRegisteredUserId(@PathVariable Long registeredUserId) {

        List<Term> terms = termService.findPastScheduledTermsByRegisteredUserId(registeredUserId, LocalDateTime.now(), "SCHEDULED");

        List<TermDTO> termsDTO = new ArrayList<>();
        for (Term t: terms) {
            termsDTO.add(new TermDTO(t));
        }

        return new ResponseEntity<>(termsDTO, HttpStatus.OK);
    }
}
