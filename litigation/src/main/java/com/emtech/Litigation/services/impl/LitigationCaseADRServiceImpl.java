package com.emtech.Litigation.services.impl;

import com.emtech.Litigation.models.LitigationCase;
import com.emtech.Litigation.repositories.LitigationCaseRepository;
import com.emtech.Litigation.services.LitigationCaseADRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LitigationCaseADRServiceImpl implements LitigationCaseADRService {

    private final LitigationCaseRepository litigationCaseRepository;

    @Autowired
    public LitigationCaseADRServiceImpl(LitigationCaseRepository litigationCaseRepository) {
        this.litigationCaseRepository = litigationCaseRepository;
    }

    @Override
    public void initiateADRForLitigationCase(Long caseId) {
        Optional<LitigationCase> litigationCaseOptional = litigationCaseRepository.findById(caseId);
        if (litigationCaseOptional.isPresent()) {
            LitigationCase litigationCase = litigationCaseOptional.get();
            litigationCase.setStatus("Under ADR");
            litigationCaseRepository.save(litigationCase);
            // Additional logic to initiate the ADR process
            System.out.println("ADR process initiated for case ID: " + caseId);
        } else {
            throw new IllegalArgumentException("Litigation case not found with ID: " + caseId);
        }
    }
}
