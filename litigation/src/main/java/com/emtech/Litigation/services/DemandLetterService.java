package com.emtech.Litigation.services;

import com.emtech.Litigation.models.DemandLetter;

import java.util.List;

public interface DemandLetterService {
            //void generateDemandLetter(String caseReferenceNumber);


    List<DemandLetter> findAll();

    DemandLetter save(DemandLetter demandLetter);

    String generateDemandLetter(String caseReferenceNumber);

    DemandLetter viewDemandLetter(Long id);

    DemandLetter markAsSent(Long id);
}


