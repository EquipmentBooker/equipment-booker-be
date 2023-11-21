package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.PredefinedTerm;
import com.example.equipment_booker.model.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PredefinedTermDTO {
    private Long id;

    private LocalDateTime startTime;

    private int duration;

    private String status;

    private CompanyAdministratorDTO companyAdministrator;

    public PredefinedTermDTO(PredefinedTerm predefinedTerm) {
        this.id = predefinedTerm.getId();
        this.startTime = predefinedTerm.getStartTime();
        this.duration = predefinedTerm.getDuration();
        this.status = predefinedTerm.getStatus();
        this.companyAdministrator = new CompanyAdministratorDTO(predefinedTerm.getCompanyAdministrator());
    }
}
