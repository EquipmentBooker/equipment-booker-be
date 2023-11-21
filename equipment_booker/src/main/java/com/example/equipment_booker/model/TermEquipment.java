package com.example.equipment_booker.model;

import com.example.equipment_booker.dto.TermEquipmentDTO;
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
@Table(name="term_equipment")
public class TermEquipment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "term_id", referencedColumnName = "id")
    private Term term;

    public TermEquipment(TermEquipmentDTO te) {
        this.id = te.getId();
        this.quantity = te.getQuantity();
        this.equipment = new Equipment(te.getEquipment());
        this.term = new Term(te.getTerm());
    }
}
