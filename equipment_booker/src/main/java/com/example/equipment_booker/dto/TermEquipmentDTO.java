package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.model.Term;
import com.example.equipment_booker.model.TermEquipment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TermEquipmentDTO {
    private Long id;
    private int quantity;
    private EquipmentDTO equipment;
    private TermDTO term;

    public TermEquipmentDTO(TermEquipment termEquipment) {
        this.id = termEquipment.getId();
        this.quantity = termEquipment.getQuantity();
        this.equipment = new EquipmentDTO(termEquipment.getEquipment());
    }
}
