package com.emtech.Litigation.services.impl;
import com.emtech.Litigation.models.DemandLetter;
import com.emtech.Litigation.models.LitigationCase;
import com.emtech.Litigation.repositories.DemandLetterRepository;
import com.emtech.Litigation.repositories.LitigationCaseRepository;
import com.emtech.Litigation.services.DemandLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DemandLetterServiceImpl implements DemandLetterService {

    private final DemandLetterRepository demandLetterRepository;
    private final LitigationCaseRepository litigationCaseRepository;

    @Autowired
    public DemandLetterServiceImpl(DemandLetterRepository demandLetterRepository, LitigationCaseRepository litigationCaseRepository) {
        this.demandLetterRepository = demandLetterRepository;
        this.litigationCaseRepository = litigationCaseRepository;
    }

    public List<DemandLetter> findAll() {
        return demandLetterRepository.findAll();
    }

    public DemandLetter save(DemandLetter demandLetter) {
        return demandLetterRepository.save(demandLetter);
    }

    // Method to generate a demand letter
    public String generateDemandLetter(String caseReferenceNumber) {
        LitigationCase litigationCase = litigationCaseRepository.findByCaseReferenceNumber(caseReferenceNumber);
        if (litigationCase == null) {
            throw new IllegalArgumentException("Litigation case not found with case reference number " + caseReferenceNumber);
        }

        // Assuming DemandLetter has a constructor or setters to set these values
        DemandLetter demandLetter = new DemandLetter();
        demandLetter.setRefNumber(litigationCase.getCaseReferenceNumber());
        demandLetter.setDate(new Date()); // Set current date
        demandLetter.setClientName(litigationCase.getFirstName() + " " + litigationCase.getLastName());
        demandLetter.setClientAddress(litigationCase.getPostalAddress());
        // Set other fields
        // generating a simple text representation
        String letter;
        letter = "Dear " + demandLetter.getClientName() + ",\n\n" +
                "This is to notify you of the outstanding amount due under your loan agreement.\n\n" +
                "Please make the payment by " + demandLetter.getPaymentDeadline() + ".\n\n" +
                "Thank you,\n" +
                "Your Bank";
        return letter;
    }

    // Method to view a demand letter by its ID
    public DemandLetter viewDemandLetter(Long id) {
        Optional<DemandLetter> optionalDemandLetter = demandLetterRepository.findById(id);
        if (optionalDemandLetter.isPresent()) {
            return optionalDemandLetter.get();
        } else {
            throw new IllegalArgumentException("Demand letter not found with id " + id);
        }
    }

    private String generateReferenceNumber() {
        // Felix to add custom logic to generate the reference number
         return "OURREF123";
    }

    // Method to mark a demand letter as sent
    public DemandLetter markAsSent(Long id) {
        DemandLetter demandLetter = demandLetterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Demand letter not found with id " + id));
        demandLetter.setSent(true);
        return demandLetterRepository.save(demandLetter);
    }
}


