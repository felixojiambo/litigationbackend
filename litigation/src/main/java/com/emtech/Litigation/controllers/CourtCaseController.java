package com.emtech.Litigation.controllers;

import com.emtech.Litigation.dtos.CourtCaseDTO;
import com.emtech.Litigation.dtos.DocumentsDTO;
import com.emtech.Litigation.services.CourtCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/court/")
public class CourtCaseController {
    private final CourtCaseService courtCaseService;

    public CourtCaseController(CourtCaseService courtCaseService) {
        this.courtCaseService = courtCaseService;
    }

    @PostMapping("/file-case")
    public ResponseEntity<String> fileCase(@RequestBody CourtCaseDTO courtCaseDTO, @RequestParam("documents") MultipartFile[] documents) {
        // Convert MultipartFile[] to DocumentDTO[]
        List<DocumentsDTO> documentDTOs = Arrays.stream(documents)
                .map(file -> {
                    DocumentsDTO documentDTO = new DocumentsDTO();
                    documentDTO.setName(file.getOriginalFilename());
                    documentDTO.setContentType(file.getContentType());
                    try {
                        documentDTO.setContent(file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to read file", e);
                    }
                    return documentDTO;
                })
                .collect(Collectors.toList());

        courtCaseDTO.setDocuments(documentDTOs);
        courtCaseService.fileCase(courtCaseDTO);
        return new ResponseEntity<>("Case filed successfully", HttpStatus.CREATED);
    }
}
