package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.CompanyAdministratorAppeal;
import com.example.equipment_booker.model.CompanyAppeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyAppealRepository extends JpaRepository<CompanyAppeal, Long> {

    @Query("select a from CompanyAppeal a " +
            "join a.registeredUser ru join a.company c " +
            "where ru.id = :searchValue and a.answer <> ''")
    List<CompanyAppeal> findAppealsByRegisteredUserId(@Param("searchValue") Long registeredUserId);
}
