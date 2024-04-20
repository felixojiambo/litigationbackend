package com.emtech.Litigation.services;
import org.springframework.stereotype.Service;
import com.emtech.Litigation.dtos.ClientManagementDTO;

import java.util.List;
@Service
public interface LitigationCaseService {
    void processClientData(ClientManagementDTO clientManagementDTO);

    List<ClientManagementDTO> getAllLitigationCases();

    ClientManagementDTO getLitigationCaseById(Long id);
}
