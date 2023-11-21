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
public class ScheduleTermDTO {
    private Long registeredUserId;
    private Long termId;
    private List<EquipmentDTO> reservedEquipment;
}
