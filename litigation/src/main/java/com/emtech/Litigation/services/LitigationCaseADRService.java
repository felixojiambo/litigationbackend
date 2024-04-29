package com.emtech.Litigation.services;

public interface LitigationCaseADRService {
    void initiateADRForLitigationCase(Long caseId);

    long countCasesUnderADR();
}
