package com.emtech.Litigation.controllers;

import com.emtech.Litigation.dtos.ClientManagementDTO;
import com.emtech.Litigation.services.LitigationCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/litigation")
@CrossOrigin()
public class LitigationCaseController {

    private final LitigationCaseService litigationCaseService;

    @Autowired
    public LitigationCaseController(LitigationCaseService litigationCaseService) {
        this.litigationCaseService = litigationCaseService;
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCountOfLitigationCases() {
        long count = litigationCaseService.countAllLitigationCases();
        return ResponseEntity.ok(count);
    }
    @PostMapping("/{caseId}/close")
    public ResponseEntity<Void> closeLitigationCase(@PathVariable Long caseId) {
        litigationCaseService.closeLitigationCase(caseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/closed/count")
    public ResponseEntity<Long> getTotalCountOfClosedCases() {
        long count = litigationCaseService.countClosedCases();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/appealed/count")
    public ResponseEntity<Long> getTotalCountOfAppealedCases() {
        long count = litigationCaseService.countAppealedCases();
        return ResponseEntity.ok(count);
    }
    @PostMapping("/{caseId}/appeal")
    public ResponseEntity<Void> addToAppealedStatus(@PathVariable Long caseId) {
        litigationCaseService.addToAppealedStatus(caseId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/processClientData")
    public ResponseEntity<Void> processClientData(@RequestBody ClientManagementDTO clientManagementDTO) {
        try {
            litigationCaseService.processClientData(clientManagementDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientManagementDTO>> getAllLitigationCases() {
        List<ClientManagementDTO> litigationCases = litigationCaseService.getAllLitigationCases();
        return ResponseEntity.ok(litigationCases);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientManagementDTO> getLitigationCaseById(@PathVariable Long id) {
        ClientManagementDTO litigationCaseDTO = litigationCaseService.getLitigationCaseById(id);
        return ResponseEntity.ok(litigationCaseDTO);
    }
}
