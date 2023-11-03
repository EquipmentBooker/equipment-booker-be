package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

    RegisteredUser findByConfirmationToken(String confirmationToken);
}
