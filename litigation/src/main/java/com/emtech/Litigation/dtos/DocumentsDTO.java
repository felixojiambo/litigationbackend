package com.emtech.Litigation.dtos;

import com.emtech.Litigation.models.CourtCase;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsDTO {
    private String name;
    private String contentType;
    private byte[] content;
    @ManyToOne
    @JoinColumn(name = "court_case_id")
    private CourtCase courtCase;

}
