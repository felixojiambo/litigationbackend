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
//@CrossOrigin()
public class LitigationCaseController {

    private final LitigationCaseService litigationCaseService;

    @Autowired
    public LitigationCaseController(LitigationCaseService litigationCaseService) {
        this.litigationCaseService = litigationCaseService;
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
