package com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository;

import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.Court;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.CourtCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtCaseRepository extends JpaRepository <CourtCase, Long> {
List<CourtCase> findByRegion(long regionId);
List<CourtCase> findByClaimantContainingIgnoreCase(String claimant);
List<CourtCase> findByDefendantContainingIgnoreCase(String defendant);
List<CourtCase> findByHearingTypeContainingIgnoreCase(String hearingType);
}
