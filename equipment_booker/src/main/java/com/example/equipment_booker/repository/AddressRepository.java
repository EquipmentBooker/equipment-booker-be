package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
