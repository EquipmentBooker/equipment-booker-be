package com.example.equipment_booker.repository;

import com.example.equipment_booker.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select c from Company c " +
            "join c.address a join c.equipment e " +
            "where lower(c.name) like lower(concat('%', :searchValue, '%')) " +
            "or lower(a.city) like lower(concat('%', :searchValue, '%')) " +
            "or lower(a.country) like lower(concat('%', :searchValue, '%')) " +
            "or lower(a.street) like lower(concat('%', :searchValue, '%')) " +
            "or lower(e.name) like lower(concat('%', :searchValue, '%'))")
    List<Company> findCompaniesByNameOrLocation(@Param("searchValue") String searchValue);

    @Query("select c from Company c " +
            "join c.address a join c.equipment e " +
            "where (lower(c.name) like lower(concat('%', :searchValue, '%')) " +
            "or lower(a.city) like lower(concat('%', :searchValue, '%')) " +
            "or lower(a.country) like lower(concat('%', :searchValue, '%')) " +
            "or lower(e.name) like lower(concat('%', :searchValue, '%')) " +
            "or lower(a.street) like lower(concat('%', :searchValue, '%'))) " +
            "and (c.averageScore >= :lowerGrade and c.averageScore <= :higherGrade)")
    List<Company> findCompaniesByNameOrLocationAndGrade(@Param("searchValue") String searchValue, @Param("lowerGrade") int lowerGrade, @Param("higherGrade") int higherGrade);
}
