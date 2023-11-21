package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.model.TermEquipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO {

    private Long id;
    private String name;
    private String type;
    private String description;
    private int quantity;
    private CompanyDTO company;

    public EquipmentDTO(Equipment equipment) {
        this.id = equipment.getId();
        this.name = equipment.getName();
        this.type = equipment.getType();
        this.description = equipment.getDescription();
        this.quantity = equipment.getQuantity();
    }
}
