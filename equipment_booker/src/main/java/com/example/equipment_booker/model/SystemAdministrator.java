package com.example.equipment_booker.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SystemAdministrator {

    @Column(name = "is_activated")
    private boolean isActivated;
}
