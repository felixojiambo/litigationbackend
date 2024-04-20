package com.LDLS.Litigation.Project.LoanManagement;

import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("api/v1/")

public class LoanManagementController {
    @Autowired
    LoanManagementService loanManagementService;

    @PostMapping("/upload/loans")
    ResponseEntity<EntityResponse> uploadFile (@RequestParam("files") MultipartFile files) {
        try {
            EntityResponse response = loanManagementService.uploadFile(files);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return null;
        }
    }



    @GetMapping("/getAllLoans")
    ResponseEntity<EntityResponse> getAllLoans () {
        EntityResponse response = loanManagementService.getAllLoans ();
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getLoanById(@RequestParam Long id) {
        try {
            EntityResponse<LoanManagement> loanResponse = loanManagementService.getLoanById(id);
            LoanManagement loan = loanResponse.getEntity(); // Assuming getEntity() retrieves the contained entity
            if (loan != null) {
                return new ResponseEntity<>(loan, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
            }
        } catch (Exception e) {
            log.error("Error getting loan with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting loan");
        }
    }




}
