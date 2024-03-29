package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.PredefinedTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PredefinedTermRepository extends JpaRepository<PredefinedTerm, Long> {
    @Query("select pt from PredefinedTerm pt " +
            "join pt.companyAdministrator ca join ca.company c " +
            "where c.id = :companyId and pt.startTime > :currentDateTime and pt.status = :status")
    List<PredefinedTerm> findFreePredefinedTermsByCompanyId(@Param("companyId") Long companyId, @Param("currentDateTime") LocalDateTime currentDateTime, @Param("status") String status);

    @Query("select pt from PredefinedTerm pt " +
            "where pt.companyAdministrator = :administrator " +
            "and pt.startTime < :endTime " +
            "and :startTime < pt.startTime + pt.duration " +
            "and pt.status = 'FREE'")
    List<PredefinedTerm> findOverlappingTerms(
            @Param("administrator") CompanyAdministrator administrator,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
