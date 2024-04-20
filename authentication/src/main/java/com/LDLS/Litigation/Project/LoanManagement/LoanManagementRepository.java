package com.LDLS.Litigation.Project.LoanManagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanManagementRepository extends JpaRepository<LoanManagement, Long> {
    Optional<LoanManagement> findById(Long Id);
}