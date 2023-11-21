package com.example.equipment_booker.model;

import com.example.equipment_booker.dto.EquipmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="equipment")
public class Equipment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    public Equipment(EquipmentDTO e) {
        this.id = e.getId();
        this.name = e.getName();
        this.type = e.getType();
        this.description = e.getDescription();
        this.quantity = e.getQuantity();
        this.company = new Company(e.getCompany());
    }
}
