package com.emtech.Litigation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtCaseDTO {
    private String caseReferenceNumber;
   private List<PartyDTO> parties;
    private List<DocumentsDTO> documents;

}
