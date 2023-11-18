package com.example.equipment_booker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "registered_users")
public class RegisteredUser extends User {
    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "is_activated")
    private boolean isActivated;

    //@Column(name = "penalties")
    //private int penalties;

    public RegisteredUser() {}
}
