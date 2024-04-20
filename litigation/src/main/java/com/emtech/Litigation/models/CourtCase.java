package com.emtech.Litigation.models;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class CourtCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caseReferenceNumber;

    @OneToMany(mappedBy = "courtCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Party> parties;

    @OneToMany(mappedBy = "courtCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documents> documents;
}
