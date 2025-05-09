// TODO remove this if rest resource repositry works well



//package com.notetakingforeggs.WhitechapelAdviceSpringBackend.service;
//
//import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.CourtCase;
//import com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository.CourtCaseRepository;
//
//import java.util.List;
//
//public class CourtCaseServiceImpl implements  CourtCaseService{
//
//    private final CourtCaseRepository repository;
//
//    public CourtCaseServiceImpl(CourtCaseRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public List<CourtCase> findByRegion(long regionId) {
//        return repository.findByRegion(regionId);
//    }
//
//    @Override
//    public List<CourtCase> findByClaimantContainingIgnoreCase(String claimant) {
//        return repository.findByClaimantContainingIgnoreCase(claimant);
//    }
//
//    @Override
//    public List<CourtCase> findByDefendantContainingIgnoreCase(String defendant) {
//        return repository.findByDefendantContainingIgnoreCase(defendant);
//    }
//
//    @Override
//    public List<CourtCase> findByHearingTypeContainingIgnoreCase(String hearingType) {
//        return repository.findByHearingTypeContainingIgnoreCase(hearingType);
//    }
//}
