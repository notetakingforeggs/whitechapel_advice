package com.notetakingforeggs.WhitechapelAdviceSpringBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class CourtCase {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;
    // maybe add @Index here?
    @Column(nullable = false)

    private long startTimeEpoch;
    private String duration;
    private String caseId;
    private String claimant;
    private String defendant;
    private String hearingType;
    private String hearingChannel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "court_id")
    private Court court;
}
