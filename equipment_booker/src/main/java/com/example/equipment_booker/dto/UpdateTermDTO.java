package com.example.equipment_booker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTermDTO {
    private Long id;
    private LocalDateTime startTime;
    private int duration;
    private String status;
    private boolean isPredefined;
    private CompanyAdministratorDTO companyAdministrator;
    private RegisteredUserDTO registeredUser;
    private List<TermEquipmentDTO> termEquipment = new ArrayList<>();
}
