package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("select c from Contract c " +
            "join c.hospital h join c.contractEquipment ce " +
            "where lower(h.name) like lower(concat('%', :searchValue, '%'))")
    Contract findContractByHospitalName(@Param("searchValue") String searchValue);

}
