package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.model.CompanyAdministratorAppeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyAdministratorAppealRepository extends JpaRepository<CompanyAdministratorAppeal, Long> {

    @Query("select a from CompanyAdministratorAppeal a " +
            "join a.registeredUser ru join a.companyAdministrator ca " +
            "where ru.id = :searchValue and a.answer <> ''")
    List<CompanyAdministratorAppeal> findAppealsByRegisteredUserId(@Param("searchValue") Long registeredUserId);
}
