package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.model.TermEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TermEquipmentRepository extends JpaRepository<TermEquipment, Long> {

    @Query("select te from TermEquipment te " +
            "join te.term t " +
            "where t.id = :termId")
    List<TermEquipment> findTermEquipmentByTermId(@Param("termId") Long termId);
}
