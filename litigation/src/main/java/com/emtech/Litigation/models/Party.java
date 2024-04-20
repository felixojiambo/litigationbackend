package com.emtech.Litigation.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "court_case_id")
    private CourtCase courtCase;

}
