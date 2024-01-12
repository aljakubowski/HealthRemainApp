package com.alja.visit.service;

import com.alja.common.enums.VisitStatus;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.visit.dto.*;
import com.alja.visit.model.VisitEntity;
import com.alja.visit.model.mapper.VisitMapper;
import com.alja.visit.model.repository.VisitRepository;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.alja.visit.VisitLogs.*;

@Slf4j
@AllArgsConstructor
@Service
public class VisitAdminService {

    private final VisitValidationService visitValidationService;
    private final VisitQueryPredicateService visitQueryPredicateService;
    private final VisitUpdateService visitUpdateService;
    private final VisitResponseService visitResponseService;
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final VisitSorter visitSorter;
    private final LogService logService;

    public VisitSimpleResponseDTO addNewVisit(VisitNewDTO visitNewDTO) {
        logService.logOperation(ADD_VISIT.logMessage, visitNewDTO.getVisitDate().toString());

        LocalDateTime visitEndDate = visitValidationService.getVisitEndDate(visitNewDTO.getVisitDate());
        visitValidationService.validateDateWithStatus(VisitStatus.AVAILABLE, visitNewDTO.getVisitDate(), visitEndDate);
        visitValidationService.validatePhysicianAvailability(visitNewDTO.getPhysicianId(), visitNewDTO.getVisitDate());

        String physicianSpecialization = getPhysicianSpecialization(visitNewDTO.getPhysicianId());
        VisitEntity visitEntity = visitMapper.toVisitEntity(visitNewDTO, physicianSpecialization, visitEndDate);

        visitRepository.save(visitEntity);
        return getVisitSimpleResponse(visitEntity);
    }

    public VisitResponseDTO getVisitById(String visitId) {
        logService.logOperation(GET_VISIT.logMessage, visitId);
        VisitEntity visitEntity = visitValidationService.findVisitIfPresent(visitId);
        return getVisitResponse(visitEntity);
    }

    public List<VisitSimpleResponseDTO> getVisitsWithFilter(VisitFilterDTO visitFilter) {
        logService.logOperation(GET_VISITS_FILTERED.logMessage);
        if (visitFilter == null) {
            return getAllVisit();
        }
        visitValidationService.validateStatus(visitFilter.getVisitStatus());
        visitValidationService.validateDates(visitFilter);
        visitValidationService.validatePhysicianIdAndSpecialization(visitFilter);

        Predicate predicate = visitQueryPredicateService.getPredicateVisits(visitFilter);
        Sort sort = visitSorter.getDefaultSort();
        List<VisitEntity> visits = IterableUtils.toList(visitRepository.findAll(predicate, sort));
        return visits.stream().map(this::getVisitSimpleResponse).toList();
    }

    public VisitSimpleResponseDTO updateVisit(String visitId, VisitUpdateDTO visitUpdateDTO) {
        logService.logOperation(UPDATE_VISIT.logMessage, visitId);

        VisitEntity existingVisitEntity = visitValidationService.findVisitIfPresent(visitId);
        visitValidationService.validateStatus(visitUpdateDTO.getVisitStatus());
        VisitEntity updatedVisitEntity = visitUpdateService.updateVisit(existingVisitEntity, visitUpdateDTO);

        visitRepository.save(updatedVisitEntity);
        return getVisitSimpleResponse(updatedVisitEntity);
    }

    public VisitSimpleResponseDTO deleteVisitById(String visitId) {
        logService.logOperation(DELETE_VISIT.logMessage, visitId);

        VisitEntity visitEntity = visitValidationService.findVisitIfPresent(visitId);
        visitValidationService.validateIfReserved(visitEntity.getVisitStatus());
        visitRepository.delete(visitEntity);
        return getVisitSimpleResponse(visitEntity);
    }

    private List<VisitSimpleResponseDTO> getAllVisit() {
        logService.logOperation(GET_ALL_VISITS.logMessage);

        Sort sort = visitSorter.getDefaultSort();
        List<VisitEntity> visits = visitRepository.findAll(sort);
        return visits.stream().map(this::getVisitSimpleResponse).toList();
    }

    private String getPhysicianSpecialization(String physicianId) {
        PhysicianResponseDTO physicianResponseDTO = visitResponseService.getPhysicianResponse(physicianId);
        return physicianResponseDTO.getPhysiciansSpecialization();
    }

    private VisitSimpleResponseDTO getVisitSimpleResponse(VisitEntity visitEntity) {
        return visitResponseService.getVisitSimpleResponse(visitEntity);
    }

    private VisitResponseDTO getVisitResponse(VisitEntity visitEntity) {
        return visitResponseService.getVisitResponse(visitEntity);
    }

}
