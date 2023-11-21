package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.model.Term;
import com.example.equipment_booker.model.TermEquipment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TermDTO {
    private Long id;
    private LocalDateTime startTime;
    private int duration;
    private String status;
    private boolean isPredefined;
    private CompanyAdministratorDTO companyAdministrator;
    private RegisteredUserDTO registeredUser;
    private List<TermEquipmentDTO> termEquipment = new ArrayList<>();

    public TermDTO(Term t) {
        this.id = t.getId();
        this.startTime = t.getStartTime();
        this.duration = t.getDuration();
        this.status = t.getStatus();
        this.isPredefined = t.isPredefined();
        this.companyAdministrator = new CompanyAdministratorDTO(t.getCompanyAdministrator());
        this.registeredUser = new RegisteredUserDTO(t.getRegisteredUser());
        this.termEquipment = t.getTermEquipment().stream().map(TermEquipmentDTO::new).collect(Collectors.toList());
    }
}
