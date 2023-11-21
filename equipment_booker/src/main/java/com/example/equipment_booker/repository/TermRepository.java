package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.PredefinedTerm;
import com.example.equipment_booker.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("select t from Term t " +
            "join t.registeredUser ru join t.termEquipment te " +
            "where ru.id = :registeredUserId and t.startTime > :currentDateTime and t.status = :status")
    List<Term> findScheduledTermsByRegisteredUserId(@Param("registeredUserId") Long registeredUserId, @Param("currentDateTime") LocalDateTime currentDateTime, @Param("status") String status);
}
