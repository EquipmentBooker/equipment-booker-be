package com.example.equipment_booker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulePredefinedTermDTO {
    private Long registeredUserId;
    private List<EquipmentDTO> reservedEquipment;
    private PredefinedTermDTO predefinedTerm;
}
