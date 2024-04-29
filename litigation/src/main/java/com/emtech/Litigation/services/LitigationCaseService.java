package com.emtech.Litigation.services;
import org.springframework.stereotype.Service;
import com.emtech.Litigation.dtos.ClientManagementDTO;

import java.util.List;
@Service
public interface LitigationCaseService {
    long countAllLitigationCases();

    void processClientData(ClientManagementDTO clientManagementDTO);

    List<ClientManagementDTO> getAllLitigationCases();

    ClientManagementDTO getLitigationCaseById(Long id);
    void addToAppealedStatus(Long caseId);
}
