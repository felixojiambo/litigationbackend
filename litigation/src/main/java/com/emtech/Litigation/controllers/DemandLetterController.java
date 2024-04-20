package com.emtech.Litigation.controllers;
import com.emtech.Litigation.models.DemandLetter;
import com.emtech.Litigation.services.DemandLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demand-letters")
public class  DemandLetterController {
    @Autowired
    private DemandLetterService demandLetterService;

    @GetMapping
    public List<DemandLetter> getAllDemandLetters() {
        return demandLetterService.findAll();
    }

    @PostMapping
    public DemandLetter createDemandLetter(@RequestBody DemandLetter demandLetter) {
        return demandLetterService.save(demandLetter);
    }

    @GetMapping("/{id}")
    public DemandLetter getDemandLetterById(@PathVariable Long id) {
        return demandLetterService.viewDemandLetter(id);
    }

    @PostMapping("/generate")
    public String generateDemandLetter(@RequestBody String caseReferenceNumber) {
        return demandLetterService.generateDemandLetter(caseReferenceNumber);
    }

    @PostMapping("/send/{id}")
    public DemandLetter markAsSent(@PathVariable Long id) {
        return demandLetterService.markAsSent(id);
    }
}
























//import com.emtech.Litigation.services.DemandLetterService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/demand/")
//public class DemandLetterController {
//    private final DemandLetterService demandLetterService;
//
//    public DemandLetterController(DemandLetterService demandLetterService) {
//        this.demandLetterService = demandLetterService;
//    }
//
//    @PostMapping("/generate")
//    public ResponseEntity<String> generateDemandLetter(@RequestBody String caseReferenceNumber) {
//        demandLetterService.generateDemandLetter(caseReferenceNumber);
//        return new ResponseEntity<>("Demand letter generated successfully", HttpStatus.OK);
//    }
//    }
