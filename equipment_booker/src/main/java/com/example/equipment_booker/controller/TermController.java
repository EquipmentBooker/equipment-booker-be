package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.PredefinedTermDTO;
import com.example.equipment_booker.dto.TermDTO;
import com.example.equipment_booker.model.PredefinedTerm;
import com.example.equipment_booker.model.Term;
import com.example.equipment_booker.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/terms")
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping(value = "/scheduled/registered-user/{registeredUserId}")
    public ResponseEntity<List<TermDTO>> getScheduledTermsByRegisteredUserId(@PathVariable Long registeredUserId) {

        List<Term> terms = termService.findScheduledTermsByRegisteredUserId(registeredUserId, LocalDateTime.now(), "SCHEDULED");

        List<TermDTO> termsDTO = new ArrayList<>();
        for (Term t: terms) {
            termsDTO.add(new TermDTO(t));
        }

        return new ResponseEntity<>(termsDTO, HttpStatus.OK);
    }
}
