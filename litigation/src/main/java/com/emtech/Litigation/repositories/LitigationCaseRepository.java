package com.emtech.Litigation.repositories;

import com.emtech.Litigation.models.LitigationCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LitigationCaseRepository extends JpaRepository<LitigationCase, Long> {
    LitigationCase findByCaseReferenceNumber(String caseReferenceNumber);

    // Example: Find all litigation cases with a specific status
    List<LitigationCase> findByStatus(String status);

    // Example: Find all litigation cases updated after a specific date
    List<LitigationCase> findByLastUpdatedAfter(Date lastUpdated);

    // Example: Find all litigation cases with a specific loan amount range
    List<LitigationCase> findByLoanAmountBetween(Long minLoanAmount, Long maxLoanAmount);

    // Example: Find all litigation cases with a specific client code
    List<LitigationCase> findByClientCode(String clientCode);

    // Example: Custom query method using @Query annotation
    @Query("SELECT lc FROM LitigationCase lc WHERE lc.status = ?1 AND lc.lastUpdated > ?2")
    List<LitigationCase> findByStatusAndLastUpdatedAfter(String status, Date lastUpdated);

    long countByStatus(String status);
}
