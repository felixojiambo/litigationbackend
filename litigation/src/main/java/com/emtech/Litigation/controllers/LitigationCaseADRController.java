package com.emtech.Litigation.controllers;
import com.emtech.Litigation.services.LitigationCaseADRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/litigation")
public class LitigationCaseADRController {

    private final LitigationCaseADRService litigationCaseADRService;

    @Autowired
    public LitigationCaseADRController(LitigationCaseADRService litigationCaseADRService) {
        this.litigationCaseADRService = litigationCaseADRService;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCountOfCasesUnderADR() {
        long count = litigationCaseADRService.countCasesUnderADR();
        return ResponseEntity.ok(count);
    }
    @PostMapping("/{caseId}/adr")
    public ResponseEntity<Void> initiateADR(@PathVariable Long caseId) {
        litigationCaseADRService.initiateADRForLitigationCase(caseId);
        return ResponseEntity.ok().build();
    }
}
