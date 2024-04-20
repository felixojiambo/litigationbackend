package com.LDLS.Litigation.Project.ClientManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientManagementRepository extends JpaRepository<ClientManagement, Long> {
    List<ClientManagement> findByStatus(String status);
    Optional<ClientManagement> findByClientCode(String clientCode);
    @Query("SELECT e FROM ClientManagement e WHERE (:clientCode IS NULL OR e.clientCode = :clientCode) AND (:loanAccNo IS NULL OR e.loanAccNo = :loanAccNo)")
    List<ClientManagement> findByClientCodeOrLoanAccNo(String clientCode, Long loanAccNo);
    long countByStatus(String status);
}

